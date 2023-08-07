package com.home.torrent.torrent.page.collect.database

import androidx.room.Room
import com.home.baseapp.app.HomeApp
import com.home.torrent.torrent.page.collect.database.table.TorrentDatabase


val torrentDb by lazy {
    Room.databaseBuilder(HomeApp.context, TorrentDatabase::class.java, "db_torrent").build()
}