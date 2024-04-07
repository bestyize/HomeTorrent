package com.home.torrent.collect.database.dao

import androidx.annotation.Keep
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.home.torrent.collect.database.bean.CollectTorrentInfo
import com.home.torrent.collect.database.bean.TB_TORRENT_COLLECT

@Keep
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

    @Query("UPDATE $TB_TORRENT_COLLECT SET title =:newTitle WHERE hash =:hash")
    suspend fun modifyTorrentTitle(newTitle: String, hash: String): Int
}