package com.oguzhanturkmen.myplantapp.di

import android.content.Context
import androidx.room.Room
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.oguzhanturkmen.myplantapp.data.datasource.PlantDataSource
import com.oguzhanturkmen.myplantapp.data.repo.PlantRepo
import com.oguzhanturkmen.myplantapp.retrofit.APIUtils
import com.oguzhanturkmen.myplantapp.retrofit.PlantAPI
import com.oguzhanturkmen.myplantapp.room.PlantDao
import com.oguzhanturkmen.myplantapp.room.PlantDatabase
import com.oguzhanturkmen.myplantapp.room.PlantFavDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun providePlantRepo(kds: PlantDataSource): PlantRepo {
        return PlantRepo(kds)
    }

    @Provides
    @Singleton
    fun provideKisilerDataSource(
        firebaseAuth: FirebaseAuth,
        collectionReference: CollectionReference,
        plantAPI: PlantAPI,
        plantDao: PlantDao,
        favDao: PlantFavDao,


        ): PlantDataSource {
        return PlantDataSource(
            firebaseAuth,
            collectionReference,
            plantAPI,
            plantDao,
            favDao
        )
    }

    @Provides
    @Singleton
    fun providePlantAPI(): PlantAPI {
        return APIUtils.plantAPIGet()
    }
    /*
    @Provides
    @Singleton
    fun providePlantDao(@ApplicationContext context: Context): PlantDao {
        val db = Room.databaseBuilder(context, PlantDatabase::class.java, "room_plant")
            //.createFromAsset("room_plant")
            .fallbackToDestructiveMigration()
            .build()
        return db.getPlantRoomDao()
    }
     */
    @Provides
    @Singleton
    fun providePlantDao(@ApplicationContext context: Context): PlantDatabase =
        Room.databaseBuilder(context, PlantDatabase::class.java, "plant_database")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun provideBasketDao(basketPlantDatabase: PlantDatabase): PlantDao =
        basketPlantDatabase.getPlantRoomDao()

    @Provides
    @Singleton
    fun provideFavDao(favProductDatabase: PlantDatabase ): PlantFavDao =
        favProductDatabase.getPlanFavDao()

    @Provides
    @Singleton
    fun provideAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideFirebaseStore(): FirebaseStorage = FirebaseStorage.getInstance()

    @Provides
    @Singleton
    fun provideFirebaseFirestore(): CollectionReference =
        FirebaseFirestore.getInstance().collection("User")
}

