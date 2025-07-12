package com.pm.airlineexplorer.airlineexplorerlist.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import com.pm.airlineexplorer.airlineexplorerlist.dao.model.AirlineEntity

@Database(
    entities = [AirlineEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun airlineDao(): AirlineDao
}