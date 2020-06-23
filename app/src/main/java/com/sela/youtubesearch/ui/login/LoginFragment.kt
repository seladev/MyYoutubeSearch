package com.sela.youtubesearch.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.sela.youtubesearch.R
import com.sela.youtubesearch.data.repository.video.VideoRepository
import com.sela.youtubesearch.ui.YoutubeActivity
import com.sela.youtubesearch.ui.base.BaseFragment
import com.sela.youtubesearch.ui.search.SearchViewModel
import com.sela.youtubesearch.ui.search.SearchViewModelFactory
import kotlinx.android.synthetic.main.fragment_login.*
import com.sela.youtubesearch.utils.showToast

/**
 * LoginScreen
 */
class LoginFragment : BaseFragment() {

    override var resourceLayout = R.layout.fragment_login
    override var titleResource = R.string.login

    private val viewModel: LoginViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        val viewModelFactory = LoginViewModelFactory( activity.application)
        ViewModelProvider(this, viewModelFactory).get(LoginViewModel::class.java)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.error.observe(viewLifecycleOwner, Observer {
            login_error.text = it
            this@LoginFragment.showToast("Login failed")
        })

        viewModel.loginSuccessIsAdmin.observe(viewLifecycleOwner, Observer {
            this@LoginFragment.showToast("Login success" )
            (activity as YoutubeActivity).openNextScreenAfterLogin(it)
        })

        login_button.setOnClickListener {
            viewModel.onLogin(
                user_name.text.toString(),
                password.text.toString(), admin_check_box.isChecked)
        }

    }
}