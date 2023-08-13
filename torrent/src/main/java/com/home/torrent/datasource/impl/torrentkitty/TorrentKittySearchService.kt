package com.home.torrent.datasource.impl.torrentkitty

import com.home.torrent.datasource.impl.base.TorrentSearchService
import com.home.torrent.def.KEY_MAGNET_PREFIX
import com.home.torrent.def.KEY_REPLACE_PAGE
import com.home.torrent.def.KEY_REPLACE_WORD
import com.home.torrent.def.TorrentSrc
import com.home.torrent.def.magnetUrlToHash
import com.home.torrent.def.magnetUrlToTorrentUrl
import com.home.torrent.def.urlEncode
import com.home.torrent.model.TorrentInfo
import com.home.torrent.util.get
import org.jsoup.Jsoup


private const val OFFICIAL_URL = "https://www.torrentkitty.tv"

private const val BASE_URL = "${OFFICIAL_URL}/search/$KEY_REPLACE_WORD/$KEY_REPLACE_PAGE"

class TorrentKittySearchService : TorrentSearchService {
    override fun search(key: String, page: Int): List<TorrentInfo> {
        val requestUrl =
            BASE_URL.replace(KEY_REPLACE_WORD, key.urlEncode).replace(KEY_REPLACE_PAGE, "$page")

        val list = mutableListOf<TorrentInfo>()

        runCatching {
            val resp = get(requestUrl)
            val items =
                Jsoup.parse(resp).body().getElementById("archiveResult")?.getElementsByTag("tr")
                    ?.takeIf { it.size > 1 } ?: return emptyList()
            items.forEachIndexed { _, element ->
                runCatching {
                    val title = element.getElementsByClass("name")[0].text()
                    val size = element.getElementsByClass("size")[0].text()
                    val date = element.getElementsByClass("date")[0].text()
                    val details = element.getElementsByClass("action")[0].getElementsByTag("a")
                    val link = details[0].attr("href")
                    val magnetUrl = details[1].attr("href")


                    list.add(
                        TorrentInfo(
                            hash = magnetUrl.magnetUrlToHash,
                            title = title,
                            detailUrl = OFFICIAL_URL + link,
                            magnetUrl = magnetUrl,
                            torrentUrl = magnetUrl.magnetUrlToTorrentUrl,
                            size = size,
                            date = date,
                            src = TorrentSrc.TORRENT_KITTY.ordinal
                        )
                    )
                }
            }
        }


        return list
    }

    override fun findMagnetUrl(url: String): String {
        if (url.startsWith(KEY_MAGNET_PREFIX)) return url
        if (url.startsWith(OFFICIAL_URL)) return KEY_MAGNET_PREFIX + url.substring(url.lastIndexOf("/") + 1)
        runCatching {
            val resp = get(url)
            return Jsoup.parse(resp).body().getElementsByClass("magnet-link")[0].text()
        }
        return ""
    }
}