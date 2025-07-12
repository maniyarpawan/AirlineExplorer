package com.pm.airlineexplorer.details.domain.model

data class AirlineDetails(
    val id: String,
    val name: String,
    val country: String,
    val headquarters: String,
    val fleet_size: Int,
    val website: String,
    val logo_url: String
)
