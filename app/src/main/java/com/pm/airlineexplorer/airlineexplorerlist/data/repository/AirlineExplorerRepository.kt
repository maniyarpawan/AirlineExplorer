package com.pm.airlineexplorer.airlineexplorerlist.data.repository

import com.pm.airlineexplorer.airlineexplorerlist.data.sources.AirlineDataSource
import com.pm.airlineexplorer.airlineexplorerlist.data.sources.model.AirlineResponse
import com.pm.airlineexplorer.airlineexplorerlist.data.sources.model.FavouriteItemResponse
import javax.inject.Inject

class AirlineExplorerRepository @Inject constructor(private val dataSource: AirlineDataSource) {

    suspend fun fetchAirlineListOfRecords() : Result<List<AirlineResponse>> = dataSource.fetchAirlineListOfRecords()

    suspend fun favouriteItem(airlineId: String): Result<Boolean> = dataSource.favouriteItem(airlineId)

    suspend fun fetchAllFavouriteItem(): Result<List<FavouriteItemResponse>> = dataSource.fetchAllFavouriteItem()
}