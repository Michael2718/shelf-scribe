package com.example.shelf_scribe.model.api

import kotlinx.serialization.Serializable

@Serializable
data class VolumesSearchResponse(
    val items: List<Volume>
)
