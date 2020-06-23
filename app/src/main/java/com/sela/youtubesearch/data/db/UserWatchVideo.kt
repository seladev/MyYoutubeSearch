package com.sela.youtubesearch.data.db

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sela.youtubesearch.data.model.StatisticsObjectInterface
import com.sela.youtubesearch.data.model.Video
import com.sela.youtubesearch.utils.getTimeFormat

/**
 * Statistic event to save user watching video
 */
@Entity(tableName = "watch_video_table")
data class UserWatchVideo(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    @ColumnInfo(name = "user_name") val userName: String,
    @ColumnInfo(name = "start_time_milli") val startTimeMilli:Long =  System.currentTimeMillis(),
    @Embedded  val video: Video,
    @ColumnInfo(name = "duration_milli") val durationMilli:Float = 0F)
    : StatisticsObjectInterface
{
    override fun getAction() :String = "watch: ${video.title} duration ${durationMilli.toLong().getTimeFormat()}"
}