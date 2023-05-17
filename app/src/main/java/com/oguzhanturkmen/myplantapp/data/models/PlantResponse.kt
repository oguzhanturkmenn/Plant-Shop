package com.oguzhanturkmen.myplantapp.data.models

data class PlantResponse(
    val `data`: List<Plant>,
    var success: Int?,
    val message: String,
    val status: String
)