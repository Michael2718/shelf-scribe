package com.example.shelf_scribe.ui

import com.example.shelf_scribe.network.SearchRequestStatus
import com.example.shelf_scribe.network.SubjectsRequestStatus
import com.example.shelf_scribe.network.VolumeRequestStatus

data class ShelfScribeUiState(
    val subjectsRequestStatus: SubjectsRequestStatus,

    val query: String,
    val isSearching: Boolean,
    val searchRequestStatus: SearchRequestStatus,

    val volumeRequestStatus: VolumeRequestStatus,
    val currentVolumeId: String
)
