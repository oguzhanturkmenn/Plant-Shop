package com.oguzhanturkmen.myplantapp.retrofit

import com.oguzhanturkmen.myplantapp.utils.Constants


class APIUtils {
    companion object {
        private const val BASE_URL = Constants.BASE_URL
        fun plantAPIGet(): PlantAPI {
            return RetrofitBuilder
                .getClient(BASE_URL)
                .create(PlantAPI::class.java)
        }
    }
}