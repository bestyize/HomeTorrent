package com.home.torrent.torrent.page.collect.database.table

import androidx.room.Database
import androidx.room.RoomDatabase
import com.home.torrent.torrent.page.collect.database.bean.CollectTorrentInfo
import com.home.torrent.torrent.page.collect.database.dao.CollectTorrentDao

@Database(entities = [CollectTorrentInfo::class], version = 1)
abstract class TorrentDatabase : RoomDatabase() {

    abstract fun collectDao(): CollectTorrentDao

}