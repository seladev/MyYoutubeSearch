package com.sela.youtubesearch.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sela.youtubesearch.data.repository.user.UserRepository
import com.sela.youtubesearch.utils.isValidEmail
import com.sela.youtubesearch.utils.isValidPassword

/**
 * Created by seladev
 */
class LoginViewModel(val userRepository: UserRepository) : ViewModel() {

    private val _error = MutableLiveData<String>()
    val error : LiveData<String>
        get() = _error

    private val _loginSuccessIsAdmin = MutableLiveData<Boolean>()
    val loginSuccessIsAdmin : LiveData<Boolean>
        get() = _loginSuccessIsAdmin


    fun onLogin(userName: String, password: String, admin: Boolean) {
        val errorMsg = StringBuilder()
        if(!userName.isValidEmail()) errorMsg.append("Email is not valid").append("\n")
        if(!password.isValidPassword()) errorMsg.append("Password must contain at least 6 characters ").append("\n")

        if(errorMsg.isEmpty()) {
            userRepository.saveCurrentUser(userName)
            _loginSuccessIsAdmin.value = admin
        }
        else{
            _error.value = errorMsg.toString()
        }
    }


}