package com.example.shelf_scribe.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.shelf_scribe.ShelfScribeApplication
import com.example.shelf_scribe.data.ShelfScribeRepository
import com.example.shelf_scribe.network.SearchRequestStatus
import com.example.shelf_scribe.network.VolumeRequestStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class ShelfScribeViewModel(
    private val shelfScribeRepository: ShelfScribeRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        ShelfScribeUiState(
            query = "",
            isSearching = false,
            searchRequestStatus = SearchRequestStatus.Loading,
            volumeRequestStatus = VolumeRequestStatus.Loading
        )
    )
    val uiState: StateFlow<ShelfScribeUiState> = _uiState

    fun searchVolumes(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update {
                it.copy(
                    searchRequestStatus = try {
                        SearchRequestStatus.Success(
                            volumes = shelfScribeRepository.searchVolumes(query)
                        )
                    } catch (e: IOException) {
                        SearchRequestStatus.Error
                    } catch (e: HttpException) {
                        SearchRequestStatus.Error
                    }
                )
            }
        }
    }

    fun updateQuery(query: String) {
        _uiState.update {
            it.copy(
                query = query
            )
        }
    }

    fun isSearching(isSearching: Boolean) {
        _uiState.update {
            it.copy(
                isSearching = isSearching
            )
        }
    }

    fun getVolume(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update {
                it.copy(
                    volumeRequestStatus = try {
                        VolumeRequestStatus.Success(
                            shelfScribeRepository.getVolumeById(id)
                        )
                    } catch (e: IOException) {
                        VolumeRequestStatus.Error
                    } catch (e: HttpException) {
                        VolumeRequestStatus.Error
                    }
                )
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as ShelfScribeApplication)
                val shelfScribeRepository = application.container.shelfScribeRepository
                ShelfScribeViewModel(
                    shelfScribeRepository = shelfScribeRepository
                )
            }
        }
    }
}