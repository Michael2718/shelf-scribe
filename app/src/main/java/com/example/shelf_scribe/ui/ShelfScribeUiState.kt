package com.example.shelf_scribe.ui

import com.example.shelf_scribe.network.SearchRequestStatus

data class ShelfScribeUiState(
    val query: String,
    val isSearching: Boolean,
    val searchRequestStatus: SearchRequestStatus
)
