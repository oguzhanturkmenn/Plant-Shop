package com.oguzhanturkmen.myplantapp.ui.dashboard

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.oguzhanturkmen.myplantapp.data.models.Answer
import com.oguzhanturkmen.myplantapp.data.models.Plant
import com.oguzhanturkmen.myplantapp.data.models.PlantFavModel
import com.oguzhanturkmen.myplantapp.data.models.User
import com.oguzhanturkmen.myplantapp.data.repo.PlantRepo
import com.oguzhanturkmen.myplantapp.ui.basket.BasketViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    var plantRepo: PlantRepo,
    var firebaseAuth: FirebaseAuth

) : ViewModel() {
    var userLive = MutableLiveData<User>()
    val list = MutableLiveData<List<Plant>>()

    fun getDatas() {
        viewModelScope.launch {
            list.value = plantRepo.getAllPlants()
        }
    }

    fun getLiveUser() {
        Log.d("deneme1", plantRepo.getLiveUser().value.toString())
        userLive = plantRepo.getLiveUser()
    }

    fun addToBasket(plant: Plant) {
        viewModelScope.launch(Dispatchers.IO) {
            plantRepo.addToBasket(plant)
        }
    }

    fun addToFav(favPlant: PlantFavModel) {
        viewModelScope.launch {
            plantRepo.addToFav(favPlant)

        }
    }

    fun deleteFavPlant(favPlant: PlantFavModel) {
        viewModelScope.launch {
            plantRepo.deleteFromFav(favPlant)
            getDatas()
        }
    }
}