package com.sela.youtubesearch.data.repository

import android.content.SharedPreferences

/**
 * app SharedPreferences to store data in SharedPreferences
 */
class YoutubeSearchPreferencesManager(private val preferences:SharedPreferences) {
    companion object{
        private const val CURRENT_USER = "CURRENT_USER"
    }

    /**
     * current login user
     */
    var currentUser : String?
        get() = preferences.getString(CURRENT_USER, null)
        set(value) = preferences.edit().putString(CURRENT_USER, value).apply()

}