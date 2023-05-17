package com.oguzhanturkmen.myplantapp.retrofit

import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RetrofitBuilder {
    companion object {
        private val gson = GsonBuilder()
            .setLenient()
            .create()

        fun getClient(base_url: String): Retrofit {
            return Retrofit.Builder()
                .baseUrl(base_url)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        }
    }
}

