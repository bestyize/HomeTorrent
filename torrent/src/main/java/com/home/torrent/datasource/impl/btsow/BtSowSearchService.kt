package com.home.torrent.datasource.impl.btsow

import com.home.torrent.datasource.impl.base.TorrentSearchService
import com.home.torrent.def.KEY_REPLACE_PAGE
import com.home.torrent.def.KEY_REPLACE_WORD
import com.home.torrent.def.TorrentSrc
import com.home.torrent.def.hashToTorrentUrl
import com.home.torrent.def.urlEncode
import com.home.torrent.model.TorrentInfo
import com.home.torrent.util.get
import org.jsoup.Jsoup


// https://btsow.com/search
private const val BASE_SEARCH_URL =
    "https://btsow.com/search/$KEY_REPLACE_WORD/page/$KEY_REPLACE_PAGE"

internal class BtSowSearchService : TorrentSearchService {
    override fun search(key: String, page: Int): List<TorrentInfo> {
        val originUrl = BASE_SEARCH_URL.replace(KEY_REPLACE_WORD, key.urlEncode)
            .replace(KEY_REPLACE_PAGE, page.toString())
        val content = get(originUrl)
        return parseSearch(content)
    }

    override fun findMagnetUrl(url: String): String {
        runCatching {
            val content = get(url)
            return Jsoup.parse(content).body().getElementById("magnetLink")?.text() ?: ""
        }
        return ""
    }


    private fun parseSearch(resp: String): List<TorrentInfo> {
        val list = mutableListOf<TorrentInfo>()
        runCatching {
            val dataList = Jsoup.parse(resp).body()
                .getElementsByClass("data-list")[0].getElementsByClass("row")
            dataList.forEach {
                runCatching {
                    if (it.getElementsByClass("size").size == 0) return@forEach
                    val size = it.getElementsByClass("size")[0].text()
                    val date = it.getElementsByClass("date").text()
                    val link = "https:" + it.getElementsByTag("a")[0].attr("href")
                    val title = it.getElementsByClass("file").text()
                    val hash = link.substring(link.lastIndexOf("/") + 1)
                    val torrentUrl = hash.hashToTorrentUrl
                    list.add(
                        TorrentInfo(
                            hash = hash.uppercase(),
                            title = title,
                            detailUrl = link,
                            torrentUrl = torrentUrl,
                            date = date,
                            size = size,
                            src = TorrentSrc.BT_SOW.ordinal
                        )
                    )
                }
            }
        }
        return list
    }
}