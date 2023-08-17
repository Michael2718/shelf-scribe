package com.example.shelf_scribe.network

import com.example.shelf_scribe.model.api.ExtendedVolume
import com.example.shelf_scribe.model.api.Volume

sealed interface SearchRequestStatus {
    data class Success(
        val volumes: List<Volume>
    ) : SearchRequestStatus

    object Error : SearchRequestStatus
    object Loading : SearchRequestStatus
}

sealed interface VolumeRequestStatus {
    data class Success(
        val volume: ExtendedVolume
    ) : VolumeRequestStatus

    object Error : VolumeRequestStatus
    object Loading : VolumeRequestStatus
}