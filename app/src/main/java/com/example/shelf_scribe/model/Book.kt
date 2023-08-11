package com.example.shelf_scribe.model

import androidx.annotation.DrawableRes
import kotlinx.serialization.Serializable

@Serializable
data class Book(
    val id: String,
    @DrawableRes
    val thumbnailResourceId: Int
)
