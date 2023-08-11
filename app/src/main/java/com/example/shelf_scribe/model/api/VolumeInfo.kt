package com.example.shelf_scribe.model.api

import kotlinx.serialization.Serializable

@Serializable
data class VolumeInfo(
    val title: String,
    val imageLinks: ImageLinks
)
