package com.pm.airlineexplorer.details.state

import androidx.lifecycle.SavedStateHandle
import com.pm.airlineexplorer.airlineexplorerlist.domain.model.Airline
import com.pm.airlineexplorer.common.Event
import com.pm.airlineexplorer.common.StateHolder
import com.pm.airlineexplorer.common.launchFlow
import com.pm.airlineexplorer.details.domain.model.AirlineDetails
import com.pm.airlineexplorer.details.domain.usecases.AirlineDetailsUseCase
import com.pm.airlineexplorer.navigation.Screen
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class AirlineDetailsStateHolder @Inject constructor(
private val airlineDetailsUseCase: AirlineDetailsUseCase,
    savedStateHandle: SavedStateHandle
): StateHolder<AirlineDetailsStateHolder.Params, AirlineDetailsStateHolder.UiState>() {

    override val params = Params(
        airlineId = savedStateHandle.get<String>(Screen.NavParams.PARAM_AIRLINE_ID) ?: "1"
    )
    private val initialValue = InternalState(
        id = "1",
        name = "Airline",
        country = "India",
        headquarters = "Pune",
        fleet_size = 101,
        website = "https://www.google.com/",
        logo_url = "abc",
        isLoading = false
    )

    private val fetchAirlineDetails = launchFlow {
        _state.update {
            it.copy(
                isLoading = true
            )
        }

        params.airlineId?.let {
            airlineDetailsUseCase(AirlineDetailsUseCase.Params(it)).collect { result ->
                if (result.isSuccess) {
                    val details = result.getOrNull()
                    _state.update {
                        it.copy(
                            id = details?.id ?: "1",
                            name = details?.name ?: "Airline",
                            country = details?.country ?: "India",
                            headquarters = details?.headquarters ?: "Pune",
                            fleet_size = details?.fleet_size ?: 100,
                            website = details?.website ?: "https://www.google.com/",
                            logo_url = details?.logo_url ?: "Abc",
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
    }

    private val _state = MutableStateFlow((initialValue))

    override val state: Flow<UiState> = combine(
        _state,
        fetchAirlineDetails
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
        id = id,
        name = name,
        country = country,
        headquarters = headquarters,
        fleet_size = fleet_size,
        website = website,
        logo_url = logo_url,
        isLoading = isLoading
    )

    data class UiState(
        val id: String,
        val name: String,
        val country: String,
        val headquarters: String,
        val fleet_size: Int,
        val website: String,
        val logo_url: String,
        val isLoading: Boolean
    )

    data class InternalState(
        val id: String,
        val name: String,
        val country: String,
        val headquarters: String,
        val fleet_size: Int,
        val website: String,
        val logo_url: String,
        val isLoading: Boolean
    )

    data class Params(val airlineId: String)
}