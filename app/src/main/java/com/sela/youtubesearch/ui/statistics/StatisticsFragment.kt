package com.sela.youtubesearch.ui.statistics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import com.sela.youtubesearch.R
import com.sela.youtubesearch.ui.base.BaseFragment
import com.sela.youtubesearch.utils.setVisible
import com.sela.youtubesearch.utils.show
import com.sela.youtubesearch.utils.showToast
import kotlinx.android.synthetic.main.fragment_statistics.*

/**
 * Statistics screen - only available to admin
 */
class StatisticsFragment:BaseFragment() {

    override var resourceLayout = R.layout.fragment_statistics
    override var titleResource = R.string.statistics

    private lateinit var statisticsAdapter: StatisticsAdapter

    private val viewModel: StatisticsViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        val viewModelFactory = StatisticsViewModelFactory( activity.application)
        ViewModelProvider(this, viewModelFactory).get(StatisticsViewModel::class.java)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        statisticsAdapter = StatisticsAdapter()

        statistics_recycler_view.addItemDecoration(DividerItemDecoration(statistics_recycler_view.context, DividerItemDecoration.VERTICAL))
        statistics_recycler_view.adapter = statisticsAdapter

        viewModel.allStatistics.observe(viewLifecycleOwner, Observer {
            showToast("Load Statistics ${if(it.isNullOrEmpty()) "failed" else "success"}")
            statistics_recycler_view.show()
            statisticsAdapter.data = it
        })

        viewModel.errorStatistics.observe(viewLifecycleOwner, Observer {
            statistics_message.setVisible(it.first)
            statistics_message.text = it.second
        })


    }

}