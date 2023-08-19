package com.example.shelf_scribe.model.api

import kotlinx.serialization.Serializable

@Serializable
data class ExtendedVolume(
    val id: String,
    val volumeInfo: ExtendedVolumeInfo
)

@Serializable
data class ExtendedVolumeInfo(
    val title: String = "",
    val authors: List<String> = listOf(""),
    val publisher: String = "",
    val publishedDate: String = "",
    val description: String = "",
    val pageCount: Int = 0,
    val imageLinks: ExtendedImageLinks? = null
)

@Serializable
data class ExtendedImageLinks(
    val thumbnail: String? = null,
    val small: String? = null,
    val medium: String? = null
)
