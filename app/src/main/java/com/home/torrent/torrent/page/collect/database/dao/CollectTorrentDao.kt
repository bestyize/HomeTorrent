package com.home.torrent.torrent.page.collect.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.home.torrent.torrent.page.collect.database.bean.CollectTorrentInfo
import com.home.torrent.torrent.page.collect.database.bean.TB_TORRENT_COLLECT

@Dao
interface CollectTorrentDao {

    @Query("SELECT * FROM $TB_TORRENT_COLLECT")
    suspend fun loadCollectedTorrent(): List<CollectTorrentInfo>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(data: CollectTorrentInfo): Long

    @Query("DELETE FROM $TB_TORRENT_COLLECT")
    suspend fun deleteAll(): Int

    @Query("DELETE FROM $TB_TORRENT_COLLECT WHERE id =:id")
    suspend fun deleteById(id: Int): Int

    @Query("DELETE FROM $TB_TORRENT_COLLECT WHERE magnet_url=:magnetUrl")
    suspend fun deleteByMagnetUrl(magnetUrl: String): Int
}