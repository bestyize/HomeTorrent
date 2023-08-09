package com.home.torrent.datasource.impl.cilimao

import com.home.torrent.datasource.impl.base.TorrentSearchService
import com.home.torrent.def.KEY_MAGNET_PREFIX
import com.home.torrent.def.KEY_REPLACE_PAGE
import com.home.torrent.def.KEY_REPLACE_WORD
import com.home.torrent.def.TorrentSrc
import com.home.torrent.def.urlEncode
import com.home.torrent.model.TorrentInfo
import com.home.torrent.util.get
import org.jsoup.Jsoup


private const val OFFICIAL_URL = "https://clmclm.com"

private const val BASE_URL = "${OFFICIAL_URL}/search-$KEY_REPLACE_WORD-0-0-$KEY_REPLACE_PAGE.html"
class CilimaoSearchService : TorrentSearchService {

    override fun search(key: String, page: Int): List<TorrentInfo> {
        val list = mutableListOf<TorrentInfo>()
        val requestUrl = BASE_URL.replace(KEY_REPLACE_WORD, key.urlEncode).replace(KEY_REPLACE_PAGE, "$page")

        runCatching {
            val respContent = get(requestUrl)
            val items = Jsoup.parse(respContent).body().getElementsByClass("ssbox")
            items.forEachIndexed { _, element ->
                runCatching {
                    val title = element.getElementsByClass("title")[0].getElementsByTag("h3")[0].text()
                    val bar = element.getElementsByClass("sbar")[0]

                    val details = bar.getElementsByTag("b")
                    val size = details[1].text()
                    val date = details[0].text()
                    val downloadCount = details[3].text()
                    val lastDownloadDate = details[2].text()

                    val link = element.getElementsByClass("title")[0].getElementsByTag("h3")[0].getElementsByAttribute("href")[0].attr("href")
                    val magnetUrl = bar.getElementsByAttribute("href").attr("href")


                    list.add(
                        TorrentInfo(
                            title = title,
                            detailUrl = OFFICIAL_URL + link,
                            magnetUrl = magnetUrl,
                            size = size,
                            date = date,
                            lastDownloadDate = lastDownloadDate,
                            downloadCount = downloadCount,
                            src = TorrentSrc.CILIMAO.ordinal
                        )
                    )
                }
            }
        }

        return list

    }

    override fun findMagnetUrl(url: String): String {
        runCatching {
            return KEY_MAGNET_PREFIX + url.substring(url.lastIndexOf("/") + 1).replace(".html", "").uppercase()
        }
        return ""
    }
}