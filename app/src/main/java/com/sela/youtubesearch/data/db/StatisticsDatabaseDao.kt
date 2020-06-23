package com.sela.youtubesearch.data.db

import androidx.lifecycle.LiveData
import androidx.room.*

/**
 * Statistics Database Dao - mapping statistic event to SQLite
 */
@Dao
interface StatisticsDatabaseDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(searchQuery: UserSearchQuery)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(watchVideo: UserWatchVideo):Long

    @Query("UPDATE watch_video_table SET duration_milli = :duration WHERE id =:id")
    fun update(duration: Float, id:Long)

    @Query("SELECT * FROM search_query_table ORDER BY id DESC")
    fun getAllSearchQueries(): LiveData<List<UserSearchQuery>>

    @Query("SELECT * FROM watch_video_table ORDER BY id DESC")
    fun getAllWatchVideos(): LiveData<List<UserWatchVideo>>
}