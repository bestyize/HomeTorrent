package com.home.torrent.datasource.impl.piratebay

import org.jsoup.Jsoup
import com.home.torrent.datasource.impl.base.TorrentSearchModel
import com.home.torrent.def.KEY_REPLACE_PAGE
import com.home.torrent.def.KEY_REPLACE_WORD
import com.home.torrent.def.TorrentSrc
import com.home.torrent.def.urlEncode
import com.home.torrent.model.TorrentInfo
import com.home.torrent.util.get

private const val BASE_URL = "https://thepiratebay.party"
private const val SEARCH_URL = "$BASE_URL/search/$KEY_REPLACE_WORD/$KEY_REPLACE_PAGE/99/0"

internal object PirateBaySearchModel : TorrentSearchModel {


    override fun searchParse(key: String, page: Int): List<TorrentInfo> {
        val list = mutableListOf<TorrentInfo>()
        val realPage = SEARCH_URL.replace(KEY_REPLACE_WORD, key.urlEncode).replace(KEY_REPLACE_PAGE, page.toString())
        val elements = Jsoup.parse(get(realPage)).body().getElementsByTag("tr")
        elements.forEach {
            runCatching {
                val tds = it.getElementsByTag("td")
                val title = tds[1].text()
                val detailLink = tds[1].getElementsByAttribute("href")[0].attr("href")
                val date = tds[2].text()
                val magnetLink = tds[3].getElementsByAttribute("href")[0].attr("href")
                val size = tds[4].text()
                list.add(TorrentInfo(title = title,size = size,src = TorrentSrc.PIRATE_BAY.ordinal,date = date,detailUrl = detailLink,magnetUrl = magnetLink))
            }
        }

        return list
    }

    override fun getMagnet(link: String?): String {
        link ?: return ""
        kotlin.runCatching {
            return Jsoup.parse(get(link)).body().getElementsByClass("download")[0].getElementsByTag("a")[0].attr("href")
        }
        return ""
    }
}