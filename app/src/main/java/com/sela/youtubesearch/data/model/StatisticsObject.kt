package com.sela.youtubesearch.data.model

/**
 * Statistics Object - for displaying statistic event in one list view
 */
data class StatisticsObject(
    val id: Long = 0L,
    val userName: String,
    val startTimeMilli:Long =  System.currentTimeMillis(),
    val action:String
) {
}

interface StatisticsObjectInterface{
    fun getAction():String
}