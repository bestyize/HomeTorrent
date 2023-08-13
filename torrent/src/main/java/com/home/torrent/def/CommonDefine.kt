package com.home.torrent.def

const val KEY_REPLACE_WORD = "key_replace_word"
const val KEY_REPLACE_PAGE = "key_replace_page"

const val KEY_DETAIL_REPLACE_DETAIL = "key_replace_detail"

const val KEY_MAGNET_PREFIX = "magnet:?xt=urn:btih:"

const val KEY_REPLACE_HASH = "key_replace_hash"

internal val COMMON_TORREND_FILE_URL = "http://itorrents.org/torrent/$KEY_REPLACE_HASH.torrent "


enum class TorrentSrc {
    SOLID_TORRENT,
    YU_HUA_GE,
    PIRATE_BAY,
    ACG_RIP,
    ZERO_MAGNET,
    X_1337,
    BT_SOW,
    TOR_LOCK,
    TORRENT_KITTY,
    CILIMAO
}