package com.oguzhanturkmen.myplantapp.room

import androidx.room.*
import com.oguzhanturkmen.myplantapp.data.models.PlantFavModel

@Dao
interface PlantFavDao {

    @Query("SELECT * FROM room_fav_plant ORDER BY fav_id DESC")
    suspend fun getAllFav(): List<PlantFavModel>

    @Query("SELECT fav_name FROM room_fav_plant")
    suspend fun getFavTitles(): List<String>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToFav(favPlant: PlantFavModel)

    @Delete
    suspend fun deleteFromFav(favPlant:PlantFavModel)


}