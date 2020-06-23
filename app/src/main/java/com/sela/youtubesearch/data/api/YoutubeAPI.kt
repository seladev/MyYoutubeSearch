package com.sela.youtubesearch.data.api

import retrofit2.http.GET
import retrofit2.http.Query


/**
 * Youtube Api - for get videos from youtube api
 */
interface YoutubeAPI {
    
    @GET("search?part=snippet")
    suspend fun searchForYoutubeVideo(
        @Query("q") searchWord: String?,
        @Query("type") type: String = "video",
        @Query("maxResults") maxResults: Int = 25
    ): YoutubeSearchResponse

}