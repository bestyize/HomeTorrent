package com.home.torrent.datasource.impl.btsow

import com.home.torrent.datasource.impl.base.TorrentSearchModel
import com.home.torrent.def.KEY_REPLACE_PAGE
import com.home.torrent.def.KEY_REPLACE_WORD
import com.home.torrent.def.TorrentSrc
import com.home.torrent.def.urlEncode
import com.home.torrent.model.TorrentInfo
import com.home.torrent.util.get
import org.jsoup.Jsoup

private const val TAG = "BtShowSearchModel"

// https://btsow.com/search
private const val BASE_SEARCH_URL =
    "https://btsow.com/search/$KEY_REPLACE_WORD/page/$KEY_REPLACE_PAGE"

internal object BtSowSearchModel : TorrentSearchModel {
    override fun searchParse(key: String, page: Int): List<TorrentInfo> {
        val originUrl = BASE_SEARCH_URL.replace(KEY_REPLACE_WORD, key.urlEncode)
            .replace(KEY_REPLACE_PAGE, page.toString())
        val content = get(getMirrorSite(originUrl))
        return parseSearch(content)
    }

    override fun getMagnet(link: String?): String {
        link ?: return ""
        runCatching {
            val content = get(getMirrorSite(link))
            return Jsoup.parse(content).body().getElementById("magnetLink")?.text() ?: ""
        }
        return ""
    }

    override fun getMirrorSite(originUrl: String): String {
        return originUrl
        //return "${MIRROR_SITE}source=57&url=${originUrl.urlEncode()}"
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
                    list.add(
                        TorrentInfo(
                            title = title,
                            detailUrl = link,
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