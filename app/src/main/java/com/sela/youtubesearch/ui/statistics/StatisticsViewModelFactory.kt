package com.sela.youtubesearch.ui.statistics

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sela.youtubesearch.data.db.StatisticsDatabase
import com.sela.youtubesearch.data.repository.statistics.StatisticsRepository
import com.sela.youtubesearch.data.repository.user.UserRepository

class StatisticsViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        val userRepository = UserRepository.getInstance(application)
        val statisticsRepository = StatisticsRepository(StatisticsDatabase.getInstance(application), userRepository)
        if (modelClass.isAssignableFrom(StatisticsViewModel::class.java)) {
            return StatisticsViewModel( statisticsRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
