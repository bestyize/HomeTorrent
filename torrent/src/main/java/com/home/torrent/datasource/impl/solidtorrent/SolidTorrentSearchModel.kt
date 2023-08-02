package com.home.torrent.datasource.impl.solidtorrent

import com.home.torrent.datasource.impl.base.TorrentSearchModel
import com.home.torrent.def.KEY_REPLACE_PAGE
import com.home.torrent.def.KEY_REPLACE_WORD
import com.home.torrent.def.urlEncode
import com.home.torrent.model.TorrentInfo
import com.home.torrent.util.get
import org.jsoup.Jsoup

private const val BASE_SEARCH_URL =
    "https://solidtorrent.to/search?q=$KEY_REPLACE_WORD&page=$KEY_REPLACE_PAGE"

private const val BASE_HOST = "https://solidtorrent.to"

internal object SolidTorrentSearchModel : TorrentSearchModel {
    override fun searchParse(key: String, page: Int): List<TorrentInfo> {
        val list = mutableListOf<TorrentInfo>()

        val content = get(
            BASE_SEARCH_URL.replace(KEY_REPLACE_WORD, key.urlEncode)
                .replace(KEY_REPLACE_PAGE, page.toString())
        )
        val resList = Jsoup.parse(content).body().getElementsByClass("search-result")
        resList.forEach { ele ->
            runCatching {
                val detailUrl =
                    BASE_HOST + ele.getElementsByTag("h5")[0].getElementsByAttribute("href")[0].attr(
                        "href"
                    )
                val title = ele.getElementsByTag("h5")[0].getElementsByAttribute("href")[0].text()
                val stats = ele.getElementsByClass("stats")[0]
                val downloadCount = stats.getElementsByTag("div")[1].text()
                val fileSize = stats.getElementsByTag("div")[2].text()
                val senderCount = stats.getElementsByTag("div")[3].text()
                val leacher = stats.getElementsByTag("div")[4].text()
                val time = stats.getElementsByTag("div")[5].text()

                val eleDown = ele.getElementsByClass("links")[0]
                val torrentLink = eleDown.getElementsByAttribute("href")[0].attr("href")
                val magnetLink = eleDown.getElementsByAttribute("href")[1].attr("href")
                list.add(
                    TorrentInfo(
                        title = title,
                        magnetUrl = magnetLink,
                        torrentUrl = torrentLink,
                        date = time,
                        size = fileSize,
                        detailUrl = detailUrl,
                        downloadCount = downloadCount,
                        leacherCount = leacher,
                        senderCount = senderCount
                    )
                )
            }

        }


        return list
    }

    override fun getMagnet(link: String?): String {
        link ?: return ""
        runCatching {
            return Jsoup.parse(get(link)).body()
                .getElementsByClass("dl-links")[0].getElementsByAttribute("href")[1].attr("href")
        }
        return ""
    }
}