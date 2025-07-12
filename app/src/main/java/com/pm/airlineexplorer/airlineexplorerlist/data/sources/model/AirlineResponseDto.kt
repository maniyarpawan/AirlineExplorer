package com.pm.airlineexplorer.airlineexplorerlist.data.sources.model

data class AirlineResponseDto(
    val id: String,
    val name: String,
    val country: String,
    val headquarters: String,
    val fleet_size: Int,
    val website: String,
    val logo_url: String,
    val isFavourite: Boolean
)

data class AirlineListResponseDto(
    val success: Boolean,
    val data: List<AirlineResponseDto>
)
