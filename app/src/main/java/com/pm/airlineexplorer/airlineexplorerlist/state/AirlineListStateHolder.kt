package com.pm.airlineexplorer.airlineexplorerlist.state

import com.pm.airlineexplorer.airlineexplorerlist.data.sources.model.FavouriteItemResponse
import com.pm.airlineexplorer.airlineexplorerlist.domain.model.Airline
import com.pm.airlineexplorer.airlineexplorerlist.domain.usecases.AirlineListUseCase
import com.pm.airlineexplorer.airlineexplorerlist.domain.usecases.FavouriteItemListUseCase
import com.pm.airlineexplorer.airlineexplorerlist.domain.usecases.FavouriteItemUseCase
import com.pm.airlineexplorer.airlineexplorerlist.state.AirlineListStateHolder.UiState.AirlineUiState
import com.pm.airlineexplorer.common.Event
import com.pm.airlineexplorer.common.StateHolder
import com.pm.airlineexplorer.common.launchFlow
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@ViewModelScoped
class AirlineListStateHolder @Inject constructor(
    private val airlineListUseCase: AirlineListUseCase,
    private val favouriteItemListUseCase: FavouriteItemListUseCase,
    private val favouriteItemUseCase:FavouriteItemUseCase
) : StateHolder<Unit, AirlineListStateHolder.UiState>() {

    private val initialValue = InternalState(
        airlineList = emptyList(),
        favouriteItemList = emptyList(),
        isLoading = false,
        searchQuery = ""
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

    private val fetchFavouriteAirlineList = launchFlow {
        _state.update {
            it.copy(
                isLoading = true
            )
        }

        favouriteItemListUseCase(Unit).collect { result ->
            if (result.isSuccess) {
                val tempList = result.getOrNull().orEmpty()
                _state.update {
                    it.copy(
                        favouriteItemList = tempList
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
        fetchAirlineList,
        fetchFavouriteAirlineList
    ) { internalState, _, _ ->
        internalState.toUiState()
    }

    override val initialState: UiState = initialValue.toUiState()

    fun updateSearchQuery(query: String) {
        _state.update { it.copy(searchQuery = query) }
    }

    suspend fun favouriteItem(airlineId: String) {
        favouriteItemUseCase(FavouriteItemUseCase.Params(airlineId)).collect{
            result->
            if(result.isSuccess) {
            }
        }
        // Refresh favourite list
        favouriteItemListUseCase(Unit).collect { result ->
            if (result.isSuccess) {
                _state.update {
                    it.copy(favouriteItemList = result.getOrNull().orEmpty())
                }
            }
        }
    }

    sealed interface UiEvent : Event {
        object ShowDetails : UiEvent
        data class UpdateSearchQuery(val query: String) : UiEvent
        object ClearSearchQuery : UiEvent
        data class FavouriteItem(val airlineId: String): UiEvent
    }

    internal suspend fun onUiEvent(event: UiEvent, coroutineScope: CoroutineScope) {
        when (event) {
            UiEvent.ShowDetails -> TODO()
            is UiEvent.UpdateSearchQuery -> updateSearchQuery(event.query)
            UiEvent.ClearSearchQuery -> updateSearchQuery("")
            is UiEvent.FavouriteItem -> coroutineScope.launch {  favouriteItem(event.airlineId) }
        }
    }


    private fun InternalState.toUiState(): UiState {
        val favouriteIds = favouriteItemList.map { it.airlineId }.toSet()

        // Filter the airlineList by searchQuery (case-insensitive)
        val filteredList = if (searchQuery.isBlank()) {
            airlineList
        } else {
            airlineList.filter {
                it.name.contains(searchQuery, ignoreCase = true) ||
                        it.country.contains(searchQuery, ignoreCase = true)
            }
        }

        return UiState(
            airlineList = filteredList.map {
                it.copy(isFavourite = favouriteIds.contains(it.id)).toUiState()
            },
            isLoading = isLoading
        )
    }

    private fun Airline.toUiState() = AirlineUiState(
        id = id,
        name = name,
        country = country,
        headquarters = headquarters,
        fleet_size = fleet_size,
        website = website,
        logo_url = logo_url,
        isFavourite = isFavourite
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
            val logo_url: String,
            val isFavourite: Boolean
        )
    }

    data class InternalState(
        val airlineList: List<Airline> = emptyList(),
        val favouriteItemList: List<FavouriteItemResponse> = emptyList(),
        val isLoading: Boolean,
        val searchQuery: String
    )


}