package com.example.shelf_scribe.network

import com.example.shelf_scribe.model.api.Volume

sealed interface SearchRequestStatus {
    data class Success(
        val volumes: List<Volume>
    ) : SearchRequestStatus

    object Error : SearchRequestStatus
    object Loading : SearchRequestStatus
}
