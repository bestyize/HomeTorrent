package com.home.torrent.datasource

import com.home.torrent.datasource.impl.acgrip.AcgRipSearchService
import com.home.torrent.datasource.impl.base.TorrentSearchService
import com.home.torrent.datasource.impl.btsow.BtSowSearchService
import com.home.torrent.datasource.impl.piratebay.PirateBaySearchService
import com.home.torrent.datasource.impl.solidtorrent.SolidTorrentSearchService
import com.home.torrent.datasource.impl.torlock.TorLockSearchService
import com.home.torrent.datasource.impl.torrentkitty.TorrentKittySearchService
import com.home.torrent.datasource.impl.x1337.X1337SearchService
import com.home.torrent.datasource.impl.yuhuage.YuHuaGeSearchService
import com.home.torrent.datasource.impl.zeromagnet.ZeroMagnetSearchService
import com.home.torrent.def.TorrentSrc
import com.home.torrent.model.TorrentSource


fun newTorrentService(torrentSrc: Int = TorrentSrc.ZERO_MAGNET.ordinal): TorrentSearchService {
    return servicesPool[torrentSrc] ?: ZeroMagnetSearchService()
}

internal fun getSupportTorrentSources(): List<TorrentSource> = listOf(
    TorrentSource(
        src = TorrentSrc.SOLID_TORRENT.ordinal,
        title = "Solid",
        officialUrl = "https://solidtorrent.to"
    ),
    TorrentSource(
        src = TorrentSrc.BT_SOW.ordinal, title = "BtSow", officialUrl = "https://btsow.com"
    ),
    TorrentSource(
        src = TorrentSrc.TORRENT_KITTY.ordinal,
        title = "Kitty",
        officialUrl = "https://www.torrentkitty.tv/"
    ),
    TorrentSource(
        src = TorrentSrc.ZERO_MAGNET.ordinal, title = "Zero", officialUrl = "https://0magnet.com"
    ),
    TorrentSource(
        src = TorrentSrc.X_1337.ordinal, title = "1337", officialUrl = "https://www.1337xx.to"
    ),
    TorrentSource(
        src = TorrentSrc.TOR_LOCK.ordinal,
        title = "TorLock",
        officialUrl = "https://www.torlock.com"
    ),
    TorrentSource(
        src = TorrentSrc.ACG_RIP.ordinal, title = "AcgRip", officialUrl = "https://acg.rip"
    ),
    TorrentSource(src = TorrentSrc.PIRATE_BAY.ordinal, title = "海盗湾", officialUrl = ""),
)

private val servicesPool by lazy {
    mapOf(
        TorrentSrc.ZERO_MAGNET.ordinal to ZeroMagnetSearchService(),
        TorrentSrc.X_1337.ordinal to X1337SearchService(),
        TorrentSrc.PIRATE_BAY.ordinal to PirateBaySearchService(),
        TorrentSrc.TOR_LOCK.ordinal to TorLockSearchService(),
        TorrentSrc.BT_SOW.ordinal to BtSowSearchService(),
        TorrentSrc.YU_HUA_GE.ordinal to YuHuaGeSearchService(),
        TorrentSrc.ACG_RIP.ordinal to AcgRipSearchService(),
        TorrentSrc.SOLID_TORRENT.ordinal to SolidTorrentSearchService(),
        TorrentSrc.TORRENT_KITTY.ordinal to TorrentKittySearchService()
    )
}