package com.sela.youtubesearch.data.repository.video

import com.sela.youtubesearch.data.model.Video

/**
 * VideoInterface - Interface for Video Repository
 */
interface VideoInterface {

    /**
     * searchForYoutubeVideo  - search for youtube video
     */
    suspend fun searchForYoutubeVideo(searchWord: String): List<Video>
}
