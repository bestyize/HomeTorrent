package com.home.torrent.datasource.impl.x1337

import com.home.torrent.datasource.impl.base.TorrentSearchModel
import com.home.torrent.def.KEY_REPLACE_PAGE
import com.home.torrent.def.KEY_REPLACE_WORD
import com.home.torrent.def.MIRROR_SITE
import com.home.torrent.def.TorrentSrc
import com.home.torrent.def.urlEncode
import com.home.torrent.model.TorrentInfo
import com.home.torrent.util.get
import org.jsoup.Jsoup

private const val TAG = "X1337SearchModel"

private const val BASE_SEARCH_URL =
    "https://www.1337xx.to/search/$KEY_REPLACE_WORD/$KEY_REPLACE_PAGE/"
private const val BASE_DETAIL_URL = "https://www.1337xx.to"

internal object X1337SearchModel : TorrentSearchModel {

    override fun searchParse(key: String, page: Int): List<TorrentInfo> {
        val reqUrl = BASE_SEARCH_URL.replace(KEY_REPLACE_WORD, key.urlEncode)
            .replace(KEY_REPLACE_PAGE, page.toString())
        val resp = get(getMirrorSite(reqUrl))
        return parseSearch(resp)
    }

    private fun parseSearch(resp: String): List<TorrentInfo> {
        val list = mutableListOf<TorrentInfo>()
        val body = Jsoup.parse(resp).body()
        runCatching {
            val items = body.getElementsByTag("tbody")[0].getElementsByTag("tr")
            items.forEach {
                val item1 = it.getElementsByClass("coll-1 name")
                val fileName = item1.text()
                val link = BASE_DETAIL_URL + item1[0].getElementsByTag("a")[1].attr("href")
                val size = it.getElementsByClass("coll-4 size mob-uploader").text()
                list.add(
                    TorrentInfo(
                        title = fileName,
                        detailUrl = link,
                        size = size,
                        src = TorrentSrc.X_1337.ordinal
                    )
                )

            }
            return list
        }
        return listOf()

    }

    override fun getMagnet(link: String?): String {
        link ?: return ""
        val body = Jsoup.parse(get(getMirrorSite(link)))
        runCatching {
            return body.getElementsByClass(MAGNET_CLASS_NAME)[0].attr(
                "href"
            )
        }
        return ""

    }

    override fun getMirrorSite(originUrl: String): String {
        //return originUrl
        return "${MIRROR_SITE}source=4&url=${originUrl.urlEncode}"
    }


    private const val MAGNET_CLASS_NAME = "torrentdown2"

}