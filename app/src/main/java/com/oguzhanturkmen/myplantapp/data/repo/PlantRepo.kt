package com.oguzhanturkmen.myplantapp.data.repo

import androidx.lifecycle.LiveData
import com.oguzhanturkmen.myplantapp.data.datasource.PlantDataSource
import com.oguzhanturkmen.myplantapp.data.models.Plant
import com.oguzhanturkmen.myplantapp.data.models.PlantFavModel

class PlantRepo(var dataSource: PlantDataSource) {

    suspend fun getAllPlants(): List<Plant> {
        val favoriteNamesList = dataSource.favDao.getFavTitles().orEmpty()
        return dataSource.getAllPlants().map {
            with(it) {
                Plant(
                    id,
                    plantDescription,
                    plantImage,
                    plantPrice,
                    plantName,
                    plantCount,
                    plantEmail,
                    favoriteNamesList.contains(plantName)
                )
            }

        }
    }
    suspend fun loadFavPlants(): List<PlantFavModel> = dataSource.loadFavPlants()

    val readAllBasket: LiveData<List<Plant>> = dataSource.plantDao.getAllBasket()

    suspend fun addToFav(favPlant: PlantFavModel) = dataSource.addToFav(favPlant)

    suspend fun addToBasket(plant: Plant) = dataSource.addToBasket(plant)

    suspend fun deleteFromFav(favPlant:PlantFavModel) = dataSource.deleteFromFav(favPlant)

    suspend fun updateBasket(plant: Plant) = dataSource.updateBasket(plant)

    suspend fun getTotalPrice(): Int = dataSource.getTotalPrice()

    suspend fun deleteFromBasket(name:String) = dataSource.deleteFromBasket(name)


    fun login(email: String, password: String) = dataSource.login(email, password)

    fun register(userEmail: String, userPassword: String) =
        dataSource.register(userEmail, userPassword)

    fun getLiveUser() = dataSource.getLiveUser()

    fun updateImage(imageUrl: String) = dataSource.updateImage(imageUrl)
}