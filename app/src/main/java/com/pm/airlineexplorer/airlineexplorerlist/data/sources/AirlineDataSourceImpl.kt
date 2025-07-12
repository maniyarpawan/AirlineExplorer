package com.pm.airlineexplorer.airlineexplorerlist.data.sources

import android.content.Context
import com.pm.airlineexplorer.airlineexplorerlist.dao.AirlineDao
import com.pm.airlineexplorer.airlineexplorerlist.dao.FavouriteItemDao
import com.pm.airlineexplorer.airlineexplorerlist.dao.model.AirlineEntity
import com.pm.airlineexplorer.airlineexplorerlist.dao.model.FavouriteItemEntity
import com.pm.airlineexplorer.airlineexplorerlist.data.api.ApiAirlineService
import com.pm.airlineexplorer.airlineexplorerlist.data.sources.model.AirlineResponse
import com.pm.airlineexplorer.airlineexplorerlist.data.sources.model.AirlineResponseDto
import com.pm.airlineexplorer.airlineexplorerlist.data.sources.model.FavouriteItemResponse
import com.pm.airlineexplorer.common.NetworkUtil
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AirlineDataSourceImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val apiAirlineService: ApiAirlineService,
    private val airlineDao: AirlineDao,
    private val favouriteItemDao: FavouriteItemDao
) : AirlineDataSource {

    override suspend fun fetchAirlineListOfRecords(): Result<List<AirlineResponse>> =
        runCatching {
            val favouriteIds = getFavouriteIds()

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
                            logo_url = airline.logo_url,
                            isFavourite = favouriteIds.contains(airline.id)
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

    override suspend fun favouriteItem(airlineId: String): Result<Boolean> =
        runCatching {
            if (favouriteItemDao.isFavourite(airlineId)) {
                favouriteItemDao.deleteById(airlineId)
                false
            } else {
                favouriteItemDao.insert(FavouriteItemEntity(airlineId = airlineId))
                true
            }
        }

    override suspend fun fetchAllFavouriteItem(): Result<List<FavouriteItemResponse>> =
        runCatching {
            favouriteItemDao.getAll().map { it.toDomain() }
        }

    suspend fun getFavouriteIds(): Set<String> =
        favouriteItemDao.getAll().map { it.airlineId }.toSet()

    fun AirlineResponseDto.toDomain(): AirlineResponse {
        return AirlineResponse(
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

    private fun AirlineEntity.toDomain(): AirlineResponse = AirlineResponse(
        id = id,
        name = name,
        country = country,
        headquarters = headquarters,
        fleet_size = fleet_size,
        website = website,
        logo_url = logo_url,
        isFavourite = isFavourite
    )

    private fun FavouriteItemEntity.toDomain(): FavouriteItemResponse = FavouriteItemResponse(
        airlineId = airlineId
    )
}
