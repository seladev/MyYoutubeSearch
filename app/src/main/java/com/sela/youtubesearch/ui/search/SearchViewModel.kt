package com.sela.youtubesearch.ui.search

import android.app.Application
import androidx.lifecycle.*
import com.sela.youtubesearch.data.model.Status
import com.sela.youtubesearch.data.model.StatusType
import com.sela.youtubesearch.data.model.StatusType.Loading
import com.sela.youtubesearch.data.model.Video
import com.sela.youtubesearch.data.repository.video.VideoRepository
import com.sela.youtubesearch.utils.logError
import kotlinx.coroutines.*

/**
 * Created by seladev
 */
class SearchViewModel(
    private val videoRepository:VideoRepository, application: Application
) : AndroidViewModel(application) {

    private val _status = MutableLiveData<Status>()

    private var _queryLiveData = MutableLiveData<String>()

    val videoResult: LiveData<List<Video>> = _queryLiveData.switchMap { queryString ->
        liveData(Dispatchers.IO) {
            var currentStatus:Status
            try {
                val videoList = videoRepository.searchForYoutubeVideo(queryString)
                if(videoList.isEmpty()){
                    currentStatus =  Status(StatusType.Error,"No Result")
                }
                else{
                    currentStatus =  Status(StatusType.Success)
                }
                emit(videoList)
            }catch (e:Exception){
                currentStatus = Status(StatusType.Error, e.localizedMessage?: "Error")
                logError("search error ${e.message}")
            }
            withContext(Dispatchers.Main){
                _status.value = currentStatus
            }
        }
    }

    val loadingVideo : LiveData<Pair<Boolean, String>> = Transformations.map(_status) {
        return@map Pair(it.status == Loading, it.message)
    }

    val errorloadingVideo : LiveData<Pair<Boolean, String>> = Transformations.map(_status) {
        return@map Pair(it.status == StatusType.Error, it.message)
    }

    val showVideoList : LiveData<Boolean> = Transformations.map(_status) {
        return@map it.status == StatusType.Success
    }

    val hideKeyboard : LiveData<Boolean> = Transformations.map(_status) {
        return@map true
    }


    private val _navigateToVideoPlayer = MutableLiveData<Video>()
    val navigateToVideoPlayer : LiveData<Video>
        get() = _navigateToVideoPlayer


    fun searchVideo(searchText:String){
        _status.value = Status(Loading,"Loading")
        _queryLiveData.value = searchText
    }

    fun onVideoClick(video: Video){
        _navigateToVideoPlayer.value = video
    }

    fun onVideoNavigated(){
        _navigateToVideoPlayer.value = null
    }
}