package com.example.shelf_scribe.network

import com.example.shelf_scribe.model.api.ExtendedVolume
import com.example.shelf_scribe.model.api.VolumesSearchResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GoogleBooksApiService {
    @GET("volumes")
    suspend fun searchVolumes(@Query("q") query: String): VolumesSearchResponse

    @GET("volumes/{id}")
    suspend fun getVolumeById(@Path("id") id: String): ExtendedVolume
}
