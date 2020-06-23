package com.sela.youtubesearch.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sela.youtubesearch.data.model.StatisticsObjectInterface

/**
 * Statistic event to save user search query
 */
@Entity(tableName = "search_query_table")
data class UserSearchQuery(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    @ColumnInfo(name = "user_name") val userName: String,
    @ColumnInfo(name = "search_query") val searchQuery: String,
    @ColumnInfo(name = "start_time_milli") val startTimeMilli:Long = System.currentTimeMillis())
    : StatisticsObjectInterface
{
    override fun getAction() :String = "search: $searchQuery"
}