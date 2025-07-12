package com.pm.airlineexplorer.details.data.sources

import com.pm.airlineexplorer.details.data.sources.model.AirlineDetailsResponse

interface AirlineDetailsDataSource {
    suspend fun fetchAirlineDetails(airlineId: String): Result<AirlineDetailsResponse>
}