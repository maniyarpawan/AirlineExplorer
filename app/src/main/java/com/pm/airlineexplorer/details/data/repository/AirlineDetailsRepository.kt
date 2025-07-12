package com.pm.airlineexplorer.details.data.repository

import com.pm.airlineexplorer.airlineexplorerlist.data.sources.model.AirlineResponse
import com.pm.airlineexplorer.details.data.sources.AirlineDetailsDataSource
import com.pm.airlineexplorer.details.data.sources.model.AirlineDetailsResponse
import javax.inject.Inject

class AirlineDetailsRepository @Inject constructor(
    private val airlineDetailsDataSource: AirlineDetailsDataSource
){
    suspend fun fetchAirlineDetails(airlineId: String) : Result<AirlineDetailsResponse> = airlineDetailsDataSource.fetchAirlineDetails(airlineId)
}