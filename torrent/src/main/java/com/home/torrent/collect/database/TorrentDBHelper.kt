package com.home.torrent.collect.database

import androidx.room.Room
import com.home.baseapp.app.HomeApp
import com.home.torrent.collect.database.table.TorrentDatabase


internal val torrentDb by lazy {
    Room.databaseBuilder(HomeApp.context, TorrentDatabase::class.java, "db_torrent").build()
}