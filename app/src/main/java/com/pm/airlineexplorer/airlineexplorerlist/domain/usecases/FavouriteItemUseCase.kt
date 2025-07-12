package com.pm.airlineexplorer.airlineexplorerlist.domain.usecases

import com.pm.airlineexplorer.airlineexplorerlist.data.repository.AirlineExplorerRepository
import com.pm.airlineexplorer.airlineexplorerlist.domain.model.Airline
import com.pm.airlineexplorer.common.UseCase
import javax.inject.Inject

class FavouriteItemUseCase @Inject constructor(
    private val airlineExplorerRepository: AirlineExplorerRepository
) : UseCase<FavouriteItemUseCase.Params, Result<Boolean>>() {

    override suspend fun doWork(params: Params): Result<Boolean> {
        return airlineExplorerRepository.favouriteItem(params.airlineId)
    }

    data class Params(
        val airlineId: String
    )


}