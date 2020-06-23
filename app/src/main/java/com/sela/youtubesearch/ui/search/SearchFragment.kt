package com.sela.youtubesearch.ui.search

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import com.sela.youtubesearch.R
import com.sela.youtubesearch.data.model.Video
import com.sela.youtubesearch.data.repository.video.VideoRepository
import com.sela.youtubesearch.ui.YoutubeActivity
import com.sela.youtubesearch.ui.base.BaseFragment
import com.sela.youtubesearch.utils.*
import kotlinx.android.synthetic.main.fragment_search.*


/**
 * Search for video screen
 */
class SearchFragment : BaseFragment() {

    override var resourceLayout = R.layout.fragment_search

    private lateinit var queryTextListener: SearchView.OnQueryTextListener
    private val viewModel: SearchViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        val videoRepository = VideoRepository.getInstance(activity.application)
        val viewModelFactory = SearchViewModelFactory(videoRepository, activity.application)
        ViewModelProvider(this, viewModelFactory)
            .get(SearchViewModel::class.java)
    }

    private lateinit var videoAdapter: VideoAdapter
    private var searchView: SearchView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        videoAdapter = VideoAdapter(VideoAdapter.VideoItemClickListener {
            viewModel.onVideoClick(it)
        })

        video_recycler_view.addItemDecoration(DividerItemDecoration(video_recycler_view.context, DividerItemDecoration.VERTICAL))
        video_recycler_view.adapter = videoAdapter

        initObservers()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_search, menu)

        val searchItem = menu.findItem(R.id.action_search)
        val searchManager = activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager

        searchItem?.let {
            searchView = it.actionView as SearchView
        }
        searchView?.let {
            it.setSearchableInfo(searchManager.getSearchableInfo(activity?.componentName))

            queryTextListener = object : SearchView.OnQueryTextListener {
                override fun onQueryTextChange(newText: String): Boolean {
                    return true
                }

                override fun onQueryTextSubmit(query: String): Boolean {
                    this@SearchFragment.logDebug(query)
                    viewModel.searchVideo(query)
                    return true
                }
            }
            it.setOnQueryTextListener(queryTextListener)
        }
        super.onCreateOptionsMenu(menu, inflater)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_search -> return false
            else -> { }
        }
        searchView?.setOnQueryTextListener(queryTextListener)
        return super.onOptionsItemSelected(item)
    }

    private fun initObservers() {

        viewModel.loadingVideo.observe(viewLifecycleOwner, Observer {
            search_progress_bar.setVisible(it.first)
        })

        viewModel.errorloadingVideo.observe(viewLifecycleOwner, Observer {
            message.setVisible(it.first)
            message.text = it.second
        })

        viewModel.showVideoList.observe(viewLifecycleOwner, Observer {
            video_recycler_view.setVisible(it)
        })

        viewModel.hideKeyboard.observe(viewLifecycleOwner, Observer {
            if (it) hideKeyboard()
        })

        viewModel.navigateToVideoPlayer.observe(viewLifecycleOwner, Observer {
            it?.let {
                openVideoPlayer(it)
            }
        })

        viewModel.videoResult.observe(viewLifecycleOwner, Observer {
            it?.let {
                video_recycler_view.scrollToPosition(0)
                videoAdapter.data = it
            }
        })

    }

    private fun openVideoPlayer(video: Video) {
        (activity as YoutubeActivity).openVideoPlayer(video)
        viewModel.onVideoNavigated()
    }

}

