package com.pm.airlineexplorer.airlineexplorerlist.state

import com.pm.airlineexplorer.airlineexplorerlist.domain.model.Airline
import com.pm.airlineexplorer.airlineexplorerlist.domain.usecases.AirlineListUseCase
import com.pm.airlineexplorer.airlineexplorerlist.state.AirlineListStateHolder.UiState.AirlineUiState
import com.pm.airlineexplorer.common.Event
import com.pm.airlineexplorer.common.StateHolder
import com.pm.airlineexplorer.common.launchFlow
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@ViewModelScoped
class AirlineListStateHolder @Inject constructor(
    private val airlineListUseCase: AirlineListUseCase
) : StateHolder<Unit, AirlineListStateHolder.UiState>() {

    private val initialValue = InternalState(
        airlineList = emptyList(),
        isLoading = false
    )

    private val fetchAirlineList = launchFlow {
        _state.update {
            it.copy(
                isLoading = true
            )
        }

        airlineListUseCase(Unit).collect { result ->
            if (result.isSuccess) {
                val tempList = result.getOrNull().orEmpty()
                _state.update {
                    it.copy(
                        airlineList = tempList
                    )
                }
            }

            _state.update {
                it.copy(
                    isLoading = false
                )
            }
        }
    }

    override val params: Unit = Unit
    private val _state = MutableStateFlow((initialValue))

    override val state: Flow<UiState> = combine(
        _state,
        fetchAirlineList
    ) { internalState, _ ->
        internalState.toUiState()
    }

    override val initialState: UiState = initialValue.toUiState()

    sealed interface UiEvent : Event {
        object ShowDetails : UiEvent
    }

    internal fun onUiEvent(event: UiEvent) {
        when (event) {
            UiEvent.ShowDetails -> TODO()
        }
    }


    private fun InternalState.toUiState() = UiState(
        airlineList = airlineList.map { it.toUiState() },
        isLoading = isLoading
    )

    private fun Airline.toUiState() = AirlineUiState(
        id = id,
        name = name,
        country = country,
        headquarters = headquarters,
        fleet_size = fleet_size,
        website = website,
        logo_url = logo_url,
    )

    data class UiState(
        val airlineList: List<AirlineUiState>,
        val isLoading: Boolean
    ) {
        data class AirlineUiState(
            val id: String,
            val name: String,
            val country: String,
            val headquarters: String,
            val fleet_size: Int,
            val website: String,
            val logo_url: String
        )
    }

    data class InternalState(
        val airlineList: List<Airline> = emptyList(),
        val isLoading: Boolean
    )


}