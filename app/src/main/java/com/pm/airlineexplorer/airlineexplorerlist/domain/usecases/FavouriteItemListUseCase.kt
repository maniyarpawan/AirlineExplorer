package com.pm.airlineexplorer.airlineexplorerlist.domain.usecases

import com.pm.airlineexplorer.airlineexplorerlist.data.repository.AirlineExplorerRepository
import com.pm.airlineexplorer.airlineexplorerlist.data.sources.model.FavouriteItemResponse
import com.pm.airlineexplorer.common.UseCase
import javax.inject.Inject

class FavouriteItemListUseCase @Inject constructor(
    private val airlineExplorerRepository: AirlineExplorerRepository
): UseCase<Unit, Result<List<FavouriteItemResponse>>>() {

    override suspend fun doWork(params: Unit): Result<List<FavouriteItemResponse>> =
        airlineExplorerRepository.fetchAllFavouriteItem()
}