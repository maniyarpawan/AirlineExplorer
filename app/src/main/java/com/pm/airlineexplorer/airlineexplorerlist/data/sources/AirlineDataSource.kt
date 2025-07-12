package com.pm.airlineexplorer.airlineexplorerlist.data.sources

import com.pm.airlineexplorer.airlineexplorerlist.data.sources.model.AirlineResponse
import com.pm.airlineexplorer.airlineexplorerlist.data.sources.model.FavouriteItemResponse

interface AirlineDataSource {
    suspend fun fetchAirlineListOfRecords(): Result<List<AirlineResponse>>

    suspend fun favouriteItem(airlineId:String): Result<Boolean>

    suspend fun fetchAllFavouriteItem(): Result<List<FavouriteItemResponse>>
}