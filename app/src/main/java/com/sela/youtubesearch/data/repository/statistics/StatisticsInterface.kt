package com.sela.youtubesearch.data.repository.statistics

import com.sela.youtubesearch.data.model.Video

/**
 * StatisticsInterface - Interface for Statistics Repository
 */
interface StatisticsInterface {

    /**
     * save search event
     */
    suspend fun saveSearch(searchQuery: String)

    /**
     * save watch event
     */
    suspend fun saveWatchVideo(video: Video)

    /**
     * update watch event
     */
    suspend fun updateWatchVideo(duration: Float, id:Long)

    /**
     * get all search events
     */
    suspend fun getAllSearchStatistics()

    /**
     * get all watch video events
     */
    suspend fun getAllWatchStatistics()
}