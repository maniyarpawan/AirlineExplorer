package com.pm.airlineexplorer.airlineexplorerlist.dao.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourite")
data class FavouriteItemEntity(
    @PrimaryKey
    val airlineId: String
)
