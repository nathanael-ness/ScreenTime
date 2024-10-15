package com.spiphy.screentime.ui.screens

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.spiphy.screentime.ScreenTimeApplication
import com.spiphy.screentime.data.StarRepository
import com.spiphy.screentime.model.StarGroup
import kotlinx.coroutines.launch

sealed interface StarUiState {
    data class Success(val starGroups: List<StarGroup>) : StarUiState
    object Error : StarUiState
    object Loading : StarUiState
}

class StarViewModel(private val starRepository: StarRepository) : ViewModel() {
    var starUiState: StarUiState by mutableStateOf(StarUiState.Loading)
        private set

    init {
        getAllStars()
    }

    fun getAllStars() {
        viewModelScope.launch {
            starUiState = try {
                StarUiState.Success(starRepository.getAllGroups())
            } catch (e: Exception) {
                Log.e("TicketViewModel", "Failed to get tickets", e)
                StarUiState.Error
            }
        }
    }

    fun awardStar(note: String){
        viewModelScope.launch {
            starRepository.addStar(note)
            getAllStars()
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =(this[APPLICATION_KEY] as ScreenTimeApplication)
                val starRepository = application.container.starRepository
                StarViewModel(starRepository = starRepository)
            }
        }
    }
}