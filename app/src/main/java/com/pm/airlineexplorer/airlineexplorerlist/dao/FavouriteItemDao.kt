package com.pm.airlineexplorer.airlineexplorerlist.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pm.airlineexplorer.airlineexplorerlist.dao.model.FavouriteItemEntity

@Dao
interface FavouriteItemDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(entity: FavouriteItemEntity): Long

    @Query("SELECT * FROM favourite")
    suspend fun getAll(): List<FavouriteItemEntity>

    @Query("DELETE FROM favourite WHERE airlineId = :id")
    suspend fun deleteById(id: String)

    @Query("SELECT EXISTS(SELECT 1 FROM favourite WHERE airlineId = :id)")
    suspend fun isFavourite(id: String): Boolean
}
