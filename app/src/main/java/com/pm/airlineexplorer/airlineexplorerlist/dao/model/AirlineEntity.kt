package com.pm.airlineexplorer.airlineexplorerlist.dao.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "airline"
)
data class AirlineEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val country: String,
    val headquarters: String,
    val fleet_size: Int,
    val website: String,
    val logo_url: String,
    val isFavourite: Boolean = false
)
