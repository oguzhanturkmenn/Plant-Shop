package com.oguzhanturkmen.myplantapp.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.oguzhanturkmen.myplantapp.data.models.Plant
import com.oguzhanturkmen.myplantapp.data.models.PlantFavModel

@Dao
interface PlantDao {

    @Query("SELECT * FROM basket_table ORDER BY plant_id DESC")
    fun getAllBasket(): LiveData<List<Plant>>

    @Update
    suspend fun updateBasket(plant: Plant) // For count in adapter

    @Query("DELETE FROM basket_table WHERE plant_name = :name")
    suspend fun deleteFromBasket(name: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveBasket(plant: Plant)

    @Query("SELECT SUM(count * plant_price) FROM basket_table")
    suspend fun getTotalPrice(): Int


}