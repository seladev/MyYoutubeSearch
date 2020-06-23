package com.sela.youtubesearch.data.api

import com.squareup.moshi.Json

/**
 * Youtube search response - the response that return when making search call
 */
data class YoutubeSearchResponse (
    @field:Json(name = "items") val items : List<Items>
)

data class Items (
    @field:Json(name = "id") val id : Id,
    @field:Json(name = "snippet") val snippet : Snippet
)

data class Id (
    @field:Json(name = "videoId") val videoId : String
)

data class Snippet (
    @field:Json(name = "title") val title : String,
    @field:Json(name = "description") val description : String,
    @field:Json(name = "thumbnails") val thumbnails : Thumbnails
)

data class Thumbnails (
    @field:Json(name = "medium") val medium : Medium
)


data class Medium (
    @field:Json(name = "url") val url : String
)
