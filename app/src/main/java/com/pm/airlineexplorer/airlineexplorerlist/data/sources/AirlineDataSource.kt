package com.pm.airlineexplorer.airlineexplorerlist.data.sources

import com.pm.airlineexplorer.airlineexplorerlist.data.sources.model.AirlineResponse

interface AirlineDataSource {
    suspend fun fetchAirlineListOfRecords(): Result<List<AirlineResponse>>
}