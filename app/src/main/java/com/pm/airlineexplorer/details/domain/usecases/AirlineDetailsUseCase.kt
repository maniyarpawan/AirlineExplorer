package com.pm.airlineexplorer.details.domain.usecases

import com.pm.airlineexplorer.common.UseCase
import com.pm.airlineexplorer.details.data.repository.AirlineDetailsRepository
import com.pm.airlineexplorer.details.data.sources.model.AirlineDetailsResponse
import com.pm.airlineexplorer.details.domain.model.AirlineDetails
import javax.inject.Inject

class AirlineDetailsUseCase @Inject constructor(
    private val airlineDetailsRepository: AirlineDetailsRepository
) : UseCase<AirlineDetailsUseCase.Params, Result<AirlineDetails>>() {

    override suspend fun doWork(params: Params): Result<AirlineDetails> =
        airlineDetailsRepository.fetchAirlineDetails(params.airlineId).map { it.toDomain() }

    private fun AirlineDetailsResponse.toDomain() = AirlineDetails(
        id = id,
        name = name,
        country = country,
        headquarters = headquarters,
        fleet_size = fleet_size,
        website = website,
        logo_url = logo_url
    )

    data class Params(
        val airlineId: String
    )
}