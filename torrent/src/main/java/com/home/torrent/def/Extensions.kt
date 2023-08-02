package com.home.torrent.def

import java.net.URLEncoder

internal val String.urlEncode: String
    get() = URLEncoder.encode(this, "UTF-8")