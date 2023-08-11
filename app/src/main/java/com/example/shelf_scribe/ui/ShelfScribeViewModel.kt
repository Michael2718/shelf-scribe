package com.example.shelf_scribe.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.shelf_scribe.ShelfScribeApplication
import com.example.shelf_scribe.data.ShelfScribeRepository
import com.example.shelf_scribe.model.api.Volume
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface SearchRequestStatus {
    data class Success(val volumes: List<Volume>) : SearchRequestStatus
    object Error : SearchRequestStatus
    object Loading : SearchRequestStatus
}

class ShelfScribeViewModel(
    private val shelfScribeRepository: ShelfScribeRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        ShelfScribeUiState(
            searchRequestStatus = SearchRequestStatus.Loading
        )
    )
    val uiState: StateFlow<ShelfScribeUiState> = _uiState

    init {
        searchVolumes(
            query = "cat" // TODO: query dependency injection
        )
    }

    fun searchVolumes(query: String) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    searchRequestStatus = try {
                        SearchRequestStatus.Success(shelfScribeRepository.searchVolumes(query))
                    } catch (e: IOException) {
                        SearchRequestStatus.Error
                    } catch (e: HttpException) {
                        SearchRequestStatus.Error
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