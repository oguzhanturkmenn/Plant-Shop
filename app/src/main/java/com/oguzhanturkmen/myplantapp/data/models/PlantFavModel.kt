package com.oguzhanturkmen.myplantapp.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "room_fav_plant")
data class PlantFavModel(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "fav_id")
    var id: Int,
    @ColumnInfo(name = "fav_image")
    val plantImage: String?,
    @ColumnInfo(name = "fav_price")
    var plantPrice: Int = 1,
    @ColumnInfo(name = "fav_name")
    var plantName: String?
)