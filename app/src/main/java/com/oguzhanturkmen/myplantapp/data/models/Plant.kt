package com.oguzhanturkmen.myplantapp.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "basket_table")
data class Plant(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "plant_id")
    var id: Int,
    @ColumnInfo(name = "plant_description")
    @SerializedName("Description")
    val plantDescription: String?,
    @SerializedName("Image")
    @ColumnInfo(name = "plant_image")
    val plantImage: String?,
    @SerializedName("Price")
    @ColumnInfo(name = "plant_price")
    var plantPrice: Int = 1,
    @ColumnInfo(name = "plant_name")
    @SerializedName("PlantName")
    val plantName: String?,
    @ColumnInfo(name = "plant_count")
    var plantCount: Int=1,
    @ColumnInfo(name = "plant_email")
    var plantEmail: String?,
    @ColumnInfo(name = "plant_fav")
    var plantFav: Boolean = false


) : Serializable
{
    var count = 1
}