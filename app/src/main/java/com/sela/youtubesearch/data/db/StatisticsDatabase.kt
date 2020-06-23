package com.sela.youtubesearch.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Singleton Database for statistics events
 */
@Database(entities = [UserSearchQuery::class, UserWatchVideo::class],
    version = 1,
    exportSchema = false)
abstract class StatisticsDatabase : RoomDatabase(){

    abstract val statisticsDatabaseDao: StatisticsDatabaseDao

    companion object{
        @Volatile
        private var INSTANCE: StatisticsDatabase? = null

        fun getInstance(context: Context): StatisticsDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        StatisticsDatabase::class.java,
                        "statistics_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }

}