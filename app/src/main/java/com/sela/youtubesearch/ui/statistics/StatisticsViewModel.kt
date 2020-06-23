package com.sela.youtubesearch.ui.statistics

import androidx.lifecycle.*
import com.sela.youtubesearch.data.model.StatisticsObject
import com.sela.youtubesearch.data.model.Status
import com.sela.youtubesearch.data.model.StatusType
import com.sela.youtubesearch.data.repository.statistics.StatisticsRepository

class StatisticsViewModel(private val statisticsRepository: StatisticsRepository) : ViewModel() {

    val allStatistics = MediatorLiveData<List<StatisticsObject>>()

    private val _status = MutableLiveData<Status>()

    val errorStatistics : LiveData<Pair<Boolean, String>> = Transformations.map(_status) {
        return@map Pair(it.status == StatusType.Error, it.message)
    }

    init {
        loadAllStatistics()
    }

    /**
     * marge all statistics event and map all event to StatisticsObject
     */
    private fun loadAllStatistics() {
        _status.value = Status(StatusType.Loading)
        allStatistics.addSource(statisticsRepository.watchVideos) {
            allStatistics.value = it.map { watchVideo ->
                return@map StatisticsObject(
                    watchVideo.id,
                    watchVideo.userName,
                    watchVideo.startTimeMilli,
                    watchVideo.getAction()
                )
            }
            allStatistics.addSource(statisticsRepository.searchQueries) {
                val lastValue = allStatistics.value
                val allSearch = it.map { searchQuery ->
                    return@map StatisticsObject(
                        searchQuery.id,
                        searchQuery.userName,
                        searchQuery.startTimeMilli,
                        searchQuery.getAction()
                    )
                }


                val mergeList = mutableListOf<StatisticsObject>()
                mergeList.addAll(lastValue!!)
                mergeList.addAll(allSearch)
                mergeList.sortByDescending { it.startTimeMilli }

                if(mergeList.isNullOrEmpty()){
                    _status.value = Status(StatusType.Error, "Statistics is empty")
                }
                else{
                    _status.value = Status(StatusType.Success)
                    allStatistics.value = mergeList
                }
            }
        }
    }
}

