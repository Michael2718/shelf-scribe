package com.example.shelf_scribe.model.api

import kotlinx.serialization.Serializable

@Serializable
data class ExtendedVolume(
    val id: String,
    val volumeInfo: ExtendedVolumeInfo
)

@Serializable
data class ExtendedVolumeInfo(
    val title: String,
    val authors: List<String>,
    val description: String? = null,
    val pageCount: Int,
    val imageLinks: ExtendedImageLinks? = null
)

@Serializable
data class ExtendedImageLinks(
    val thumbnail: String? = null,
    val small: String? = null,
    val medium: String? = null
)
