package com.sela.youtubesearch.data.repository.video

import android.content.Context
import com.sela.youtubesearch.data.api.YoutubeAPI
import com.sela.youtubesearch.data.api.YoutubeRetrofitService
import com.sela.youtubesearch.data.db.StatisticsDatabase
import com.sela.youtubesearch.data.model.Video
import com.sela.youtubesearch.data.repository.statistics.StatisticsRepository
import com.sela.youtubesearch.data.repository.user.UserRepository


/**
 * VideoRepository - Repository for handle video
 */
class VideoRepository(private val statisticsRepository:StatisticsRepository) : VideoInterface {

    private val youtubeAPI =  YoutubeRetrofitService.buildService(YoutubeAPI::class.java)

    companion object{
        @Volatile
        private var INSTANCE: VideoRepository? = null

        fun getInstance(context: Context): VideoRepository {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    val userRepository = UserRepository.getInstance(context)
                    instance = VideoRepository(
                        StatisticsRepository(StatisticsDatabase.getInstance(context),
                        userRepository))
                    INSTANCE = instance
                }
                return instance
            }
        }
    }

    /**
     * searchForYoutubeVideo - make rest call for getting all videos
     * the api return extra data, we converting youtubeSearchResponse to Video object
     */
    override suspend fun searchForYoutubeVideo(searchWord: String): List<Video> {
        val youtubeSearchResponse = youtubeAPI.searchForYoutubeVideo(searchWord)
        statisticsRepository.saveSearch(searchWord)
        return youtubeSearchResponse.items.map {
            return@map Video(
            it.id.videoId,
            it.snippet.title,
            it.snippet.description,
            it.snippet.thumbnails.medium.url
        )
      }
    }
}
