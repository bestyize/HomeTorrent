package com.home.torrent.datasource.impl.piratebay

import com.home.torrent.datasource.impl.base.TorrentSearchService
import com.home.torrent.def.KEY_REPLACE_PAGE
import com.home.torrent.def.KEY_REPLACE_WORD
import com.home.torrent.def.TorrentSrc
import com.home.torrent.def.magnetUrlToHash
import com.home.torrent.def.magnetUrlToTorrentUrl
import com.home.torrent.def.urlEncode
import com.home.torrent.model.TorrentInfo
import com.home.torrent.util.get
import org.jsoup.Jsoup

private const val BASE_URL = "https://thepiratebay.party"
private const val SEARCH_URL = "$BASE_URL/search/$KEY_REPLACE_WORD/$KEY_REPLACE_PAGE/99/0"

internal class PirateBaySearchService : TorrentSearchService {
    override fun search(key: String, page: Int): List<TorrentInfo> {
        val list = mutableListOf<TorrentInfo>()
        val realPage = SEARCH_URL.replace(KEY_REPLACE_WORD, key.urlEncode)
            .replace(KEY_REPLACE_PAGE, page.toString())
        val elements = Jsoup.parse(get(realPage)).body().getElementsByTag("tr")
        elements.forEach {
            runCatching {
                val tds = it.getElementsByTag("td")
                val title = tds[1].text()
                val detailLink = tds[1].getElementsByAttribute("href")[0].attr("href")
                val date = tds[2].text()
                val magnetLink = tds[3].getElementsByAttribute("href")[0].attr("href")
                val size = tds[4].text()
                list.add(
                    TorrentInfo(
                        hash = magnetLink.magnetUrlToHash,
                        title = title,
                        size = size,
                        src = TorrentSrc.PIRATE_BAY.ordinal,
                        date = date,
                        detailUrl = detailLink,
                        magnetUrl = magnetLink,
                        torrentUrl = magnetLink.magnetUrlToTorrentUrl
                    )
                )
            }
        }

        return list
    }

    override fun findMagnetUrl(url: String): String {
        kotlin.runCatching {
            return Jsoup.parse(get(url)).body()
                .getElementsByClass("download")[0].getElementsByTag("a")[0].attr("href")
        }
        return ""
    }
}