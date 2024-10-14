package com.spiphy.screentime.ui.screens

import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.spiphy.screentime.ScreenTimeApplication
import com.spiphy.screentime.data.HistoryRepository
import com.spiphy.screentime.model.Ticket
import kotlinx.coroutines.launch

sealed interface HistoryUiState {
    data class Success(val tickets: List<Ticket>) : HistoryUiState
    object Error : HistoryUiState
    object Loading : HistoryUiState
}

class HistoryViewModel(private val historyRepository: HistoryRepository) : ViewModel() {
    var historyUiState: HistoryUiState by mutableStateOf(HistoryUiState.Loading)
        private set

    init {
        getAllTickets()
    }

    fun getAllTickets() {
        historyUiState = HistoryUiState.Loading
        viewModelScope.launch {
            historyUiState = try {
                HistoryUiState.Success(historyRepository.getAllTickets())
            } catch (e: Exception) {
                Log.e("TicketViewModel", "Failed to get tickets", e)
                HistoryUiState.Error
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =(this[APPLICATION_KEY] as ScreenTimeApplication)
                val historyRepository = application.container.historyRepository
                HistoryViewModel(historyRepository = historyRepository)
            }
        }
    }
}