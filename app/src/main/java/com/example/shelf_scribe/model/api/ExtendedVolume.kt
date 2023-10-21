package com.example.shelf_scribe.model.api

import kotlinx.serialization.Serializable

@Serializable
data class ExtendedVolume(
    val id: String,
    val volumeInfo: ExtendedVolumeInfo,
    val accessInfo: ExtendedAccessInfo
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

@Serializable
data class ExtendedAccessInfo(
    val epub: Epub,
    val pdf: Pdf,
    val accessViewStatus: String
)

@Serializable
data class Epub(
    val isAvailable: Boolean,
    val downloadLink: String = ""
)

@Serializable
data class Pdf(
    val isAvailable: Boolean,
    val downloadLink: String = ""
)

enum class AccessViewStatus {
    FULL_PURCHASED,
    FULL_PUBLIC_DOMAIN,
    SAMPLE,
    NONE
}