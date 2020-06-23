package com.sela.youtubesearch.ui.videoplayer

import androidx.lifecycle.*
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.sela.youtubesearch.data.model.Video
import com.sela.youtubesearch.data.repository.statistics.StatisticsRepository
import com.sela.youtubesearch.utils.logError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Created by seladev
 */
class VideoPlayerViewModel(private val statisticsRepository: StatisticsRepository) : ViewModel() {


    private val _currentVideo = MutableLiveData<Video>()
    val currentVideo: LiveData<Video>
        get() = _currentVideo

    private val _videoDuration = MutableLiveData<Float>()

    private val _insertUserWatchVideoId = statisticsRepository.insertUserWatchVideoId

    override fun onCleared() {
        super.onCleared()
    }

    fun setVideoToPlay(video: Video?) {
        _currentVideo.value = video
        saveWatchVideo()
    }

    fun onStateChange(state: PlayerConstants.PlayerState, currentSecond: Float) {
        when (state) {
            PlayerConstants.PlayerState.PAUSED, PlayerConstants.PlayerState.ENDED -> {
                _videoDuration.value = currentSecond
            }
            else -> {
            }
        }

    }

    fun onCloseVideoScreen(currentSecond: Float) {
        _videoDuration.value = currentSecond
        updateWatchVideo()
    }

    private fun saveWatchVideo() {
        viewModelScope.launch(Dispatchers.IO) {
            currentVideo.value?.let { video ->
                try {
                    statisticsRepository.saveWatchVideo(video)
                } catch (e: Exception) {
                    logError("Error on saveWatchVideo error = ${e.message}")
                }
            }
        }
    }

    private fun updateWatchVideo() {
        val duration = _videoDuration.value
        val id = _insertUserWatchVideoId.value

        viewModelScope.launch(Dispatchers.IO) {
            try {
                statisticsRepository.updateWatchVideo(duration ?: 0F, id ?: 0)
            } catch (e: Exception) {
                logError("Error on updateWatchVideo error = ${e.message}")
            }
        }
    }
}
