package com.oguzhanturkmen.myplantapp.ui.plant

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oguzhanturkmen.myplantapp.data.models.Plant
import com.oguzhanturkmen.myplantapp.data.models.PlantFavModel
import com.oguzhanturkmen.myplantapp.data.repo.PlantRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlantViewModel @Inject constructor(var plantRepo: PlantRepo) : ViewModel() {
    val list = MutableLiveData<List<Plant>>()

    fun getDatas() {
        viewModelScope.launch {
            list.value = plantRepo.getAllPlants()
        }
    }

    fun addToBasket(plant: Plant) {
        viewModelScope.launch(Dispatchers.IO) {
            plantRepo.addToBasket(plant)
        }
    }

    fun addToFav(favPlant:PlantFavModel) {
        viewModelScope.launch {
            plantRepo.addToFav(favPlant)
        }
    }

    fun deleteFromFav(favPlant:PlantFavModel) {
        viewModelScope.launch {
            plantRepo.deleteFromFav(favPlant)
            Log.v("Viewmodel",favPlant.plantName!!)
        }
    }
}