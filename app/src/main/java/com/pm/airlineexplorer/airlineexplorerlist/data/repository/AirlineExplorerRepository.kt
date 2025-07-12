package com.pm.airlineexplorer.airlineexplorerlist.data.repository

import com.pm.airlineexplorer.airlineexplorerlist.data.sources.AirlineDataSource
import com.pm.airlineexplorer.airlineexplorerlist.data.sources.model.AirlineResponse
import javax.inject.Inject

class AirlineExplorerRepository @Inject constructor(private val dataSource: AirlineDataSource) {

    suspend fun fetchAirlineListOfRecords() : Result<List<AirlineResponse>> = dataSource.fetchAirlineListOfRecords()
}