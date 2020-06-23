package com.sela.youtubesearch.ui.statistics

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sela.youtubesearch.R
import com.sela.youtubesearch.data.model.StatisticsObject
import com.sela.youtubesearch.utils.getDateFormat

/**
 * Created by seladev
 */

class StatisticsAdapter : RecyclerView.Adapter<StatisticsAdapter.StatisticsItemViewHolder>() {

    var data = listOf<StatisticsObject>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    class StatisticsItemViewHolder private constructor(itemView: View): RecyclerView.ViewHolder(itemView){

        companion object {
            fun from(parent: ViewGroup): StatisticsItemViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.statistics_item_view, parent, false)
                return StatisticsItemViewHolder(view)
            }
        }

        private val userNameTextView = itemView.findViewById<TextView>(R.id.user_name)
        private val actionTimeTextView = itemView.findViewById<TextView>(R.id.action_time)
        private val actionTextView = itemView.findViewById<TextView>(R.id.action)

        fun bind(statisticsObject: StatisticsObject){
            userNameTextView.text = statisticsObject.userName
            actionTimeTextView.text = statisticsObject.startTimeMilli.getDateFormat()
            actionTextView.text = statisticsObject.action
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatisticsItemViewHolder {
        return StatisticsItemViewHolder.from(parent)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: StatisticsItemViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
    }

}