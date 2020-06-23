package com.sela.youtubesearch.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.sela.youtubesearch.R
import com.sela.youtubesearch.data.model.Video
import com.sela.youtubesearch.ui.base.BaseActivity
import com.sela.youtubesearch.ui.login.LoginFragment
import com.sela.youtubesearch.ui.search.SearchFragment
import com.sela.youtubesearch.ui.statistics.StatisticsFragment
import com.sela.youtubesearch.ui.videoplayer.VideoPlayerFragment
import com.sela.youtubesearch.utils.showToast

/**
 * Main screen in app
 */
class YoutubeActivity : BaseActivity() {

    override var resourceLayout: Int = R.layout.activity_youtube

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        replaceFragment(LoginFragment())
    }


    private fun replaceFragment(fragment: Fragment, addToBackStack:Boolean = true){
        val transaction = supportFragmentManager
            .beginTransaction()
            .replace(R.id.host_fragment, fragment)
        if(addToBackStack) transaction.addToBackStack(null)
        transaction.commit()
    }


    fun openVideoPlayer(video: Video) {
        val videoPlayerFragment = VideoPlayerFragment()
        val bundle = Bundle()
        bundle.putParcelable(VideoPlayerFragment.VIDEO, video)
        videoPlayerFragment.arguments = bundle
        replaceFragment(videoPlayerFragment)
    }

    fun openNextScreenAfterLogin(isAdmin:Boolean) {
        if(isAdmin) openStatistics()
        else openSearchScreen()
    }

    private fun openSearchScreen() {
        replaceFragment(SearchFragment(), false)
    }


    private fun openStatistics() {
        replaceFragment(StatisticsFragment(), false)
    }

}