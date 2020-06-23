package com.sela.youtubesearch.ui.search

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sela.youtubesearch.data.repository.video.VideoRepository

/**
 * Created by seladev
 */
class SearchViewModelFactory(
    private val videoRepository: VideoRepository,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            return SearchViewModel(videoRepository, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}