package com.pm.airlineexplorer.airlineexplorerlist.data.api

import com.pm.airlineexplorer.airlineexplorerlist.data.sources.model.AirlineListResponseDto
import retrofit2.http.GET

interface ApiAirlineService {
    @GET("maniyarpawan/AirlineExplorer/refs/heads/main/data/airline_explorer.json") // Replace with actual path or use Mocky URL
    suspend fun getAirlines(): AirlineListResponseDto
}