package com.sela.youtubesearch.data.model

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

/**
 * Video
 */
@Parcelize
data class Video(
    @field:Json(name = "videoId") val videoId: String,
    @field:Json(name = "title") val title: String,
    @field:Json(name = "description") val description: String,
    @field:Json(name = "url") val thumbnail: String) : Parcelable {
}