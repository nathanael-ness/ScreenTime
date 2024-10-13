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
import com.spiphy.screentime.data.TicketRepository
import com.spiphy.screentime.model.Ticket
import kotlinx.coroutines.launch

sealed interface TicketUiState {

    data class Success(val tickets: List<Ticket>, val screenTime: Int) : TicketUiState
    object Error: TicketUiState
    object Loading: TicketUiState
}

class TicketViewModel(private val ticketRepository: TicketRepository) : ViewModel() {
    var ticketUiState: TicketUiState by mutableStateOf(TicketUiState.Loading)
        private set

    init {
        getTickets()
    }

    private fun update() {
        viewModelScope.launch {
            ticketUiState = try {
                TicketUiState.Success(ticketRepository.getActiveTickets(), ticketRepository.getTodaysUsedScreenTime())
            } catch (e: Exception) {
                Log.e("TicketViewModel", "Failed to get tickets", e)
                TicketUiState.Error
            }
        }
    }

    fun getTickets() {
        viewModelScope.launch {
            ticketUiState = TicketUiState.Loading
            ticketUiState = try {
                ticketRepository.createDailyTicket()
                TicketUiState.Success(ticketRepository.getActiveTickets(), ticketRepository.getTodaysUsedScreenTime())
            } catch (e: Exception) {
                Log.e("TicketViewModel", "Failed to get tickets", e)
                TicketUiState.Error
            }
        }
    }

    fun redeemTicket(ticket: Ticket) {
        viewModelScope.launch {
            ticketRepository.redeemTicket(ticket)
            update()
        }
    }

    fun awardTicket(ticketType: String, note: String) {
        viewModelScope.launch {
            ticketRepository.awardTicket(ticketType, note)
            update()
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    (this[APPLICATION_KEY] as ScreenTimeApplication)
                val ticketRepository = application.container.ticketRepository
                TicketViewModel(ticketRepository = ticketRepository)
            }
        }
    }
}