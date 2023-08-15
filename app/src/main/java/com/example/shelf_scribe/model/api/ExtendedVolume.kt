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
    val imageLinks: ExtendedImageLinks
)

@Serializable
data class ExtendedImageLinks(
    val thumbnail: String,
    val medium: String
)
