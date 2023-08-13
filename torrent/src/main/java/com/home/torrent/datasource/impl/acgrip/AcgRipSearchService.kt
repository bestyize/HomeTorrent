package com.home.torrent.datasource.impl.acgrip

import com.home.torrent.datasource.impl.base.TorrentSearchService
import com.home.torrent.def.KEY_REPLACE_PAGE
import com.home.torrent.def.KEY_REPLACE_WORD
import com.home.torrent.def.TorrentSrc
import com.home.torrent.def.urlEncode
import com.home.torrent.model.TorrentInfo
import com.home.torrent.util.get
import org.jsoup.Jsoup

private const val BASE_URL = "https://acg.rip"

private const val BASE_SEARCH_URL = "https://acg.rip/$KEY_REPLACE_PAGE?term=$KEY_REPLACE_WORD"

internal class AcgRipSearchService : TorrentSearchService {
    override fun search(key: String, page: Int): List<TorrentInfo> {
        val res: MutableList<TorrentInfo> = mutableListOf()

        runCatching {
            val originUrl = BASE_SEARCH_URL.replace(KEY_REPLACE_WORD, key.urlEncode)
                .replace(KEY_REPLACE_PAGE, page.toString())
            val content = get(originUrl)
            val validEle = Jsoup.parse(content).body().getElementsByTag("tr")

            validEle.forEach { ele ->
                runCatching {
                    val dateTime = ele.getElementsByAttribute("datetime")[0].text()
                    val title = ele.getElementsByClass("title").text()
                    val size = ele.getElementsByClass("size").text()
                    val torrentFileUrl =
                        BASE_URL + ele.getElementsByClass("action")[0].getElementsByTag("a")[0].attr(
                            "href"
                        )
                    res.add(
                        TorrentInfo(
                            detailUrl = torrentFileUrl,
                            src = TorrentSrc.ACG_RIP.ordinal,
                            date = dateTime,
                            size = size,
                            magnetUrl = torrentFileUrl,
                            torrentUrl = torrentFileUrl,
                            title = title
                        )
                    )
                }

            }
        }

        return res
    }

    override fun findMagnetUrl(url: String): String = url
}