package com.sela.youtubesearch.ui.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.sela.youtubesearch.R
import com.sela.youtubesearch.data.model.Video
import com.sela.youtubesearch.utils.loadImageOrNull
import com.squareup.picasso.Picasso


/**
 * Created by seladev
 */
class VideoAdapter(val clickListener: VideoItemClickListener) : RecyclerView.Adapter<VideoAdapter.VideoItemViewHolder>() {

    var data = listOf<Video>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class VideoItemClickListener(val clickListener: (video:Video) -> Unit) {
        fun onClick(video: Video) = clickListener(video)
    }

    class VideoItemViewHolder private constructor(itemView: View): RecyclerView.ViewHolder(itemView){

        companion object {
            fun from(parent: ViewGroup): VideoItemViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.video_item_view, parent, false)
                return VideoItemViewHolder(view)
            }
        }

        private val videoThumbnail: ImageView = itemView.findViewById(R.id.video_thumbnail)
        private val videoTitle: TextView = itemView.findViewById(R.id.video_title)
        private val videoDescription: TextView = itemView.findViewById(R.id.video_description)

        fun bind(video: Video, clickListener:VideoItemClickListener){
            Picasso.get().loadImageOrNull(video.thumbnail, videoThumbnail)
            videoTitle.text = video.title
            videoDescription.text = video.description
            itemView.setOnClickListener { clickListener.onClick(video)}
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoItemViewHolder {
        return VideoItemViewHolder.from(parent)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: VideoItemViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item, clickListener)
    }

}