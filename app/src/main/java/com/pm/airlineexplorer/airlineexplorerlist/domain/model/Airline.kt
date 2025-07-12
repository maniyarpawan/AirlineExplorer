package com.pm.airlineexplorer.airlineexplorerlist.domain.model

data class Airline(
    val id: String,
    val name: String,
    val country: String,
    val headquarters: String,
    val fleet_size: Int,
    val website: String,
    val logo_url: String
)
