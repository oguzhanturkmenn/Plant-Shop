package com.oguzhanturkmen.myplantapp.retrofit


import com.oguzhanturkmen.myplantapp.data.models.PlantResponse
import com.oguzhanturkmen.myplantapp.utils.Constants
import retrofit2.http.GET


interface PlantAPI {
    @GET(Constants.LINK)
    suspend fun getPlant(): PlantResponse
}