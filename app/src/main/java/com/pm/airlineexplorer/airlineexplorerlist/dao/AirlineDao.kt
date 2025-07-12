package com.pm.airlineexplorer.airlineexplorerlist.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pm.airlineexplorer.airlineexplorerlist.dao.model.AirlineEntity
import com.pm.airlineexplorer.details.data.sources.model.AirlineDetailsResponseDto

@Dao
interface AirlineDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(airlineEntities: List<AirlineEntity>)

    @Query("SELECT * FROM airline WHERE id= :id")
    suspend fun getAirlineDetails(id: String): AirlineDetailsResponseDto

    @Query("SELECT * FROM airline")
    suspend fun getAllAirlineEntities(): List<AirlineEntity>

    @Query("DELETE FROM airline")
    suspend fun clearAll()
}