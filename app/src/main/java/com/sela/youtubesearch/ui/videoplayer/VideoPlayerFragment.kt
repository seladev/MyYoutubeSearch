package com.sela.youtubesearch.ui.videoplayer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.YouTubePlayerTracker
import com.sela.youtubesearch.R
import com.sela.youtubesearch.data.model.Video
import com.sela.youtubesearch.ui.base.BaseFragment
import com.sela.youtubesearch.utils.logDebug
import com.sela.youtubesearch.utils.logError
import kotlinx.android.synthetic.main.fragment_video_player.*

/**
 * Video player screen
 */
class VideoPlayerFragment : BaseFragment()  {

    companion object {
        const val VIDEO = "VIDEO"
    }

    override var resourceLayout = R.layout.fragment_video_player

    var tracker = YouTubePlayerTracker()

    private val viewModel: VideoPlayerViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        val viewModelFactory = VideoPlayerViewModelFactory(activity.application)
        return@lazy ViewModelProvider(this, viewModelFactory).get(VideoPlayerViewModel::class.java)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        lifecycle.addObserver(youtube_player_view)

        youtube_player_view.addYouTubePlayerListener(tracker)
        youtube_player_view.addYouTubePlayerListener(youTubePlayerListener)
        youtube_player_view.enterFullScreen()

        val video = arguments?.getParcelable<Video>(VIDEO)
        viewModel.setVideoToPlay(video)
    }

    override fun onStop() {
        super.onStop()
        logDebug("onStop currentSecond = ${tracker.currentSecond}")
        viewModel.onCloseVideoScreen(tracker.currentSecond)

    }

    private val youTubePlayerListener = object : YouTubePlayerListener {
        override fun onApiChange(youTubePlayer: YouTubePlayer) {}
        override fun onCurrentSecond(youTubePlayer: YouTubePlayer, second: Float) {}
        override fun onPlaybackQualityChange(youTubePlayer: YouTubePlayer, playbackQuality: PlayerConstants.PlaybackQuality) {}
        override fun onPlaybackRateChange(youTubePlayer: YouTubePlayer, playbackRate: PlayerConstants.PlaybackRate) {}

        override fun onError(youTubePlayer: YouTubePlayer, error: PlayerConstants.PlayerError) {
            this@VideoPlayerFragment.logError("onError $error")
        }

        override fun onReady(youTubePlayer: YouTubePlayer) {
            this@VideoPlayerFragment.logDebug("onReady")
            val videoId = viewModel.currentVideo.value?.videoId ?: ""
            youTubePlayer.loadVideo(videoId, 0F)
        }

        override fun onStateChange(youTubePlayer: YouTubePlayer, state: PlayerConstants.PlayerState) {
            this@VideoPlayerFragment.logDebug("onStateChange state = $state ${tracker.currentSecond}")
            viewModel.onStateChange(state, tracker.currentSecond)
        }

        override fun onVideoDuration(youTubePlayer: YouTubePlayer, duration: Float) {
            this@VideoPlayerFragment.logDebug("onVideoDuration $duration")
        }

        override fun onVideoId(youTubePlayer: YouTubePlayer, videoId: String) {
            this@VideoPlayerFragment.logDebug("onVideoId $videoId")
        }

        override fun onVideoLoadedFraction(youTubePlayer: YouTubePlayer, loadedFraction: Float) {}

    }

}