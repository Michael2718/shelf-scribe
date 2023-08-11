package com.example.shelf_scribe.data

import com.example.shelf_scribe.model.api.Volume
import com.example.shelf_scribe.network.GoogleBooksApiService

interface ShelfScribeRepository {
    suspend fun searchVolumes(query: String): List<Volume>
}

class NetworkShelfScribeRepository(
    private val googleBooksApiService: GoogleBooksApiService
) : ShelfScribeRepository {
    override suspend fun searchVolumes(query: String): List<Volume> {
        return googleBooksApiService.searchVolumes(query).items
    }
}
