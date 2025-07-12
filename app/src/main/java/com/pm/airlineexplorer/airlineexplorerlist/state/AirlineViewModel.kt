package com.pm.airlineexplorer.airlineexplorerlist.state

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pm.airlineexplorer.common.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class AirlineViewModel @Inject constructor(
    private val airlineListStateHolder: AirlineListStateHolder
) : ViewModel() {

    val state: StateFlow<UiState> = airlineListStateHolder.state.map { airlineUiState ->
        UiState(
            airlineUiState = airlineUiState
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = initialValue()
    )

    private fun initialValue() = UiState(
        airlineUiState = airlineListStateHolder.initialState
    )

    data class UiState(
        val airlineUiState: AirlineListStateHolder.UiState
    )

    internal fun onUiEvent(event: Event) {
        when (event) {
            is AirlineListStateHolder.UiEvent -> airlineListStateHolder.onUiEvent(event)
        }
    }
}