package com.example.shelf_scribe.network

import com.example.shelf_scribe.model.api.ExtendedVolume
import com.example.shelf_scribe.model.api.Volume

sealed interface SubjectsRequestStatus {
    data class Success(
        val subjects: Map<String, List<Volume>>
    ) : SubjectsRequestStatus

    object Error : SubjectsRequestStatus
    object Loading : SubjectsRequestStatus
}

sealed interface SearchRequestStatus {
    object Start : SearchRequestStatus
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