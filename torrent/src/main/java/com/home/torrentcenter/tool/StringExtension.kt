package com.home.torrentcenter.tool

import java.net.URLEncoder

/**
 * @author: read
 * @date: 2023/8/16 下午11:29
 * @description:
 */

internal val String.urlEncode: String
    get() = URLEncoder.encode(this, "UTF-8")