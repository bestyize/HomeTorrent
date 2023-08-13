package com.home.torrent.datasource.impl.zeromagnet

import com.home.torrent.datasource.impl.base.TorrentSearchService
import com.home.torrent.def.KEY_DETAIL_REPLACE_DETAIL
import com.home.torrent.def.KEY_REPLACE_WORD
import com.home.torrent.def.TorrentSrc
import com.home.torrent.def.urlEncode
import com.home.torrent.model.TorrentInfo
import com.home.torrent.util.get
import org.jsoup.Jsoup

private const val BASE_SEARCH_URL = "https://0magnet.com/search?q=$KEY_REPLACE_WORD"
private const val BASE_DETAIL_URL = "https://0magnet.com$KEY_DETAIL_REPLACE_DETAIL"

internal class ZeroMagnetSearchService : TorrentSearchService {
    override fun search(key: String, page: Int): List<TorrentInfo> {
        val reqUrl = BASE_SEARCH_URL.replace(KEY_REPLACE_WORD, key.urlEncode)
        val resp = get(reqUrl)
        return parseSearch(resp)
    }

    override fun findMagnetUrl(url: String): String {
        val paramsMap =
            mapOf<String?, String?>("Referrer Policy" to "strict-origin-when-cross-origin")
        val body = Jsoup.parse(get(url, paramsMap))
        return body.getElementById("input-magnet")?.attr("value") ?: ""
    }

    private fun parseSearch(resp: String): List<TorrentInfo> {
        val list = mutableListOf<TorrentInfo>()
        val body = Jsoup.parse(resp).body()
        val elements = body.getElementsByTag("td")
        var index = 0
        while (index < elements.size - 1) {
            runCatching {
                val ele1 = elements[index]
                val ele2 = elements[index + 1]
                val id = ele1.select("a").attr("href")
                val url = BASE_DETAIL_URL.replace(KEY_DETAIL_REPLACE_DETAIL, id)
                ele1.select("p").remove()
                val title = ele1.text()
                val size = ele2.text()
                index += 2
                list.add(
                    TorrentInfo(
                        detailUrl = url,
                        title = title,
                        size = size,
                        src = TorrentSrc.ZERO_MAGNET.ordinal
                    )
                )
            }
        }
        return list
    }
}