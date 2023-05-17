package com.oguzhanturkmen.myplantapp.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.oguzhanturkmen.myplantapp.data.models.Plant
import com.oguzhanturkmen.myplantapp.data.models.PlantFavModel

@Database(entities = [Plant::class,PlantFavModel::class], version = 23)
abstract class PlantDatabase : RoomDatabase() {
    abstract fun getPlantRoomDao(): PlantDao
    abstract fun getPlanFavDao(): PlantFavDao
}