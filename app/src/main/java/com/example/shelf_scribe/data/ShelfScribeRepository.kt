package com.example.shelf_scribe.data

import com.example.shelf_scribe.model.api.ExtendedVolume
import com.example.shelf_scribe.model.api.Volume
import com.example.shelf_scribe.network.GoogleBooksApiService

interface ShelfScribeRepository {
    suspend fun getVolumesBySubject(subject: String): List<Volume>
    suspend fun searchVolumes(query: String): List<Volume>
//    suspend fun getThumbnailsFromIdList(idList: List<String>): List<String>
    suspend fun getVolumeById(id: String): ExtendedVolume
}

class NetworkShelfScribeRepository(
    private val googleBooksApiService: GoogleBooksApiService
) : ShelfScribeRepository {

    override suspend fun getVolumesBySubject(subject: String): List<Volume> {
        return googleBooksApiService.searchVolumes("subject:$subject").items
    }

    override suspend fun searchVolumes(query: String): List<Volume> {
        return googleBooksApiService.searchVolumes(query, maxResults = 40).items
    }

//    override suspend fun getThumbnailsFromIdList(idList: List<String>): List<String> {
//        return idList.map { getVolumeById(it).volumeInfo.imageLinks.medium } // Haha not thumbnail, but medium, cause thumbnails are just so small
//    }

    override suspend fun getVolumeById(id: String): ExtendedVolume {
        return googleBooksApiService.getVolumeById(id)
    }
}
