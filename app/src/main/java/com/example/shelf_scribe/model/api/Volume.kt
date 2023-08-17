package com.example.shelf_scribe.model.api

import kotlinx.serialization.Serializable

@Serializable
data class Volume(
    val id: String,
    val volumeInfo: VolumeInfo
) {
//    fun doesMatchSearchQuery(query: String): Boolean {
//        val title = volumeInfo.title
//        val matchingCombinations = listOf(
//            ""
//        )
//    }
}

@Serializable
data class VolumeInfo(
    val title: String,
    val imageLinks: ImageLinks
)

@Serializable
data class ImageLinks(
    val thumbnail: String
)
