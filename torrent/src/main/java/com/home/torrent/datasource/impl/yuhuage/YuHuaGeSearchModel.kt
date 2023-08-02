package com.home.torrent.datasource.impl.yuhuage

import com.home.torrent.datasource.impl.base.TorrentSearchModel
import com.home.torrent.def.KEY_REPLACE_PAGE
import com.home.torrent.def.KEY_REPLACE_WORD
import com.home.torrent.def.TorrentSrc
import com.home.torrent.def.urlEncode
import com.home.torrent.model.TorrentInfo
import com.home.torrent.util.get
import org.jsoup.Jsoup

private const val TAG = "YuHuaGeSearchModel"

private const val BASE_URL = "https://www.yuhuage.win"

private const val BASE_SEARCH_URL = "$BASE_URL/search/$KEY_REPLACE_WORD-$KEY_REPLACE_PAGE.html"

internal object YuHuaGeSearchModel : TorrentSearchModel {

    override fun searchParse(key: String, page: Int): List<TorrentInfo> {
        val originUrl = BASE_SEARCH_URL.replace(KEY_REPLACE_WORD, key.urlEncode)
            .replace(KEY_REPLACE_PAGE, page.toString())
        val paramsMap = mapOf<String?, String?>(
            "Referrer Policy" to "strict-origin-when-cross-origin",
            "user-agent" to "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/110.0.0.0 Safari/537.36"
        )
        return parseSearch(get(originUrl, paramsMap))
    }

    override fun getMagnet(link: String?): String {
        link ?: return ""
        runCatching {
            val paramsMap = mapOf<String?, String?>(
                "Referrer Policy" to "strict-origin-when-cross-origin",
                "user-agent" to "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/110.0.0.0 Safari/537.36",
                "cookie" to "counter=9; _gid=GA1.2.846071559.1679670300; PHPSESSID=bnu67jhrb3moqmnbvsbtdjbsq1; counter=2; __atuvc=4|12; __atuvs=641dbd64564c515e003; _gat_gtag_UA_116935724_1=1; _ga=GA1.1.1502102608.1679670300; _ga_EN97WRPCXC=GS1.1.1679670300.1.1.1679671020.0.0.0"
            )
            return Jsoup.parse(get(link, paramsMap)).body()
                .getElementsByClass("download")[0].attr("href")
        }
        return ""
    }


    private fun parseSearch(resp: String): List<TorrentInfo> {
        val list = mutableListOf<TorrentInfo>()
        runCatching {
            val items = Jsoup.parse(resp).body().getElementsByClass("search-item")
            items.forEach {
                val e1 = it.getElementsByClass("item-title")[0].getElementsByTag("a")[0]
                val title = e1.text()
                val link = BASE_URL + e1.attr("href")
                val e2 = it.getElementsByClass("item-bar")[0].getElementsByTag("span")
                val date = e2[0].text()
                val size = e2[1].text()
                list.add(
                    TorrentInfo(
                        title = title,
                        detailUrl = link,
                        date = date,
                        size = size,
                        src = TorrentSrc.YU_HUA_GE.ordinal
                    )
                )
            }
        }
        return list
    }
}