package com.pm.airlineexplorer.details.data.sources

import com.pm.airlineexplorer.airlineexplorerlist.dao.AirlineDao
import com.pm.airlineexplorer.details.data.sources.model.AirlineDetailsResponse
import com.pm.airlineexplorer.details.data.sources.model.AirlineDetailsResponseDto
import javax.inject.Inject

class AirlineDetailsDataSourceImpl @Inject constructor(
    private val airlineDao: AirlineDao
) : AirlineDetailsDataSource {
    override suspend fun fetchAirlineDetails(airlineId: String): Result<AirlineDetailsResponse> =
        kotlin.runCatching {
            airlineDao.getAirlineDetails(airlineId).toDomain()
        }

    fun AirlineDetailsResponseDto.toDomain(): AirlineDetailsResponse {
        return AirlineDetailsResponse(
            id = id,
            name = name,
            country = country,
            headquarters = headquarters,
            fleet_size = fleet_size,
            website = website,
            logo_url = logo_url
        )
    }
}