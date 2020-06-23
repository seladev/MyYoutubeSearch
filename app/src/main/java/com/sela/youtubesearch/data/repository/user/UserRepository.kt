package com.sela.youtubesearch.data.repository.user

import android.content.Context
import androidx.preference.PreferenceManager
import com.sela.youtubesearch.data.repository.YoutubeSearchPreferencesManager

/**
 * UserRepository - Repository for handle user in app
 */
class UserRepository(context : Context) : UserInterface{

    private val preferenceManager = YoutubeSearchPreferencesManager(PreferenceManager.getDefaultSharedPreferences(context))

    companion object{
        @Volatile
        private var INSTANCE: UserRepository? = null

        fun getInstance(context: Context): UserRepository {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = UserRepository(context)
                    INSTANCE = instance
                }
                return instance
            }
        }
    }

    override fun getCurrentUser(): String? = preferenceManager.currentUser

    override fun saveCurrentUser(user:String) { preferenceManager.currentUser = user}


}