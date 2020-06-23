package com.sela.youtubesearch.data.repository.statistics

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sela.youtubesearch.data.db.StatisticsDatabase
import com.sela.youtubesearch.data.db.UserSearchQuery
import com.sela.youtubesearch.data.db.UserWatchVideo
import com.sela.youtubesearch.data.model.Video
import com.sela.youtubesearch.data.repository.user.UserRepository
import com.sela.youtubesearch.utils.logDB
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * StatisticsRepository - Repository for handle statistic events
 */
class StatisticsRepository(private val database: StatisticsDatabase, userRepository: UserRepository) : StatisticsInterface {

    val searchQueries: LiveData<List<UserSearchQuery>> = database.statisticsDatabaseDao.getAllSearchQueries()
    val watchVideos: LiveData<List<UserWatchVideo>> = database.statisticsDatabaseDao.getAllWatchVideos()

    private val currentUser = userRepository.getCurrentUser() ?: ""

    private val _insertUserWatchVideoId = MutableLiveData<Long>()
    val insertUserWatchVideoId : LiveData<Long>
        get() = _insertUserWatchVideoId


    override suspend fun saveSearch(searchQuery: String) {
        withContext(Dispatchers.IO) {
            val userSearchQuery = UserSearchQuery(userName = currentUser, searchQuery = searchQuery)
            logDB("insert saveSearch $userSearchQuery")
            database.statisticsDatabaseDao.insert(userSearchQuery)
        }
    }

    override suspend fun saveWatchVideo(video:Video) {
        withContext(Dispatchers.IO) {
            val userWatchVideo = UserWatchVideo(userName = currentUser, video = video)
            val watchVideoId = database.statisticsDatabaseDao.insert(userWatchVideo)
            logDB("insert WatchVideo watchVideoId = $watchVideoId userWatchVideo = $userWatchVideo")
            withContext(Dispatchers.Main){
                _insertUserWatchVideoId.value = watchVideoId
            }
        }
    }

    override suspend fun updateWatchVideo( duration: Float, id:Long) {
        withContext(Dispatchers.IO) {
            logDB("update id = $id duration = $duration")
            database.statisticsDatabaseDao.update(duration, id)
        }
    }

    override suspend fun getAllSearchStatistics() {
        withContext(Dispatchers.IO) {
            logDB("getAllSearchQueries")
            database.statisticsDatabaseDao.getAllSearchQueries()
        }
    }

    override suspend fun getAllWatchStatistics() {
        withContext(Dispatchers.IO) {
            logDB("getAllWatchVideos")
            database.statisticsDatabaseDao.getAllWatchVideos()
        }
    }
}