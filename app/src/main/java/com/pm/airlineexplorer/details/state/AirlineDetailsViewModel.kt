package com.pm.airlineexplorer.details.state

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
class AirlineDetailsViewModel @Inject constructor(
    private val airlineDetailsStateHolder: AirlineDetailsStateHolder
) : ViewModel() {
    val state: StateFlow<UiState> = airlineDetailsStateHolder.state.map { airlineDetailsUiState ->
        UiState(
            airlineDetailsUiState = airlineDetailsUiState
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = initialValue()
    )

    private fun initialValue() = UiState(
        airlineDetailsUiState = airlineDetailsStateHolder.initialState
    )

    data class UiState(
        val airlineDetailsUiState: AirlineDetailsStateHolder.UiState
    )

    internal fun onUiEvent(event: Event) {
        when (event) {
            is AirlineDetailsStateHolder.UiEvent -> airlineDetailsStateHolder.onUiEvent(event)
        }
    }
}