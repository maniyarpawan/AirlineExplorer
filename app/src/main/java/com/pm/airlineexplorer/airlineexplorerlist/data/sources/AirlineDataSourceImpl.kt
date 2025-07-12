package com.pm.airlineexplorer.airlineexplorerlist.data.sources

import android.content.Context
import com.pm.airlineexplorer.airlineexplorerlist.dao.AirlineDao
import com.pm.airlineexplorer.airlineexplorerlist.dao.model.AirlineEntity
import com.pm.airlineexplorer.airlineexplorerlist.data.api.ApiAirlineService
import com.pm.airlineexplorer.airlineexplorerlist.data.sources.model.AirlineResponse
import com.pm.airlineexplorer.airlineexplorerlist.data.sources.model.AirlineResponseDto
import com.pm.airlineexplorer.common.NetworkUtil
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AirlineDataSourceImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val apiAirlineService: ApiAirlineService,
    private val airlineDao: AirlineDao
) : AirlineDataSource {

    override suspend fun fetchAirlineListOfRecords(): Result<List<AirlineResponse>> =
        runCatching {
            if (NetworkUtil.isNetworkAvailable(context = context)) {
                val response = apiAirlineService.getAirlines()

                if (response.success) {
                    val airlineList: List<AirlineEntity> = response.data.map { airline ->
                        AirlineEntity(
                            id = airline.id,
                            name = airline.name,
                            country = airline.country,
                            headquarters = airline.headquarters,
                            fleet_size = airline.fleet_size,
                            website = airline.website,
                            logo_url = airline.logo_url
                        )
                    }

                    if (airlineList.isNotEmpty()) {
                        airlineDao.clearAll()
                        airlineDao.insertAll(airlineList)
                    }

                    response.data.map { it.toDomain() }
                } else {
                    throw Exception("API returned success = false")
                }
            } else {
                airlineDao.getAllAirlineEntities().map { it.toDomain() }
            }
        }

    fun AirlineResponseDto.toDomain(): AirlineResponse {
        return AirlineResponse(
            id = id,
            name = name,
            country = country,
            headquarters = headquarters,
            fleet_size = fleet_size,
            website = website,
            logo_url = logo_url
        )
    }

    private fun AirlineEntity.toDomain(): AirlineResponse = AirlineResponse(
        id = id,
        name = name,
        country = country,
        headquarters = headquarters,
        fleet_size = fleet_size,
        website = website,
        logo_url = logo_url
    )
}
