package com.pm.airlineexplorer.airlineexplorerlist.domain.usecases

import com.pm.airlineexplorer.airlineexplorerlist.data.repository.AirlineExplorerRepository
import com.pm.airlineexplorer.airlineexplorerlist.data.sources.model.AirlineResponse
import com.pm.airlineexplorer.airlineexplorerlist.domain.model.Airline
import com.pm.airlineexplorer.common.UseCase
import javax.inject.Inject

class AirlineListUseCase @Inject constructor(
    private val airlineExplorerRepository: AirlineExplorerRepository
) : UseCase<Unit, Result<List<Airline>>>() {

    override suspend fun doWork(params: Unit): Result<List<Airline>> =
        airlineExplorerRepository.fetchAirlineListOfRecords().mapCatching {
            it.map { it.toDomain() }
        }

    private fun AirlineResponse.toDomain() = Airline(
        id = id,
        name = name,
        country = country,
        headquarters = headquarters,
        fleet_size = fleet_size,
        website = website,
        logo_url = logo_url,
        isFavourite = isFavourite
    )

}