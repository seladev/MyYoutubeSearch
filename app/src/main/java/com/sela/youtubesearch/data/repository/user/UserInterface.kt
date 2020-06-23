package com.sela.youtubesearch.data.repository.user

/**
 * UserInterface - Interface for User Repository
 */
interface UserInterface {

    /**
     * get current login user
     */
    fun getCurrentUser(): String?

    /**
     * save current user after login
     */
    fun saveCurrentUser(user: String)
}