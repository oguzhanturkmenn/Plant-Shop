package com.oguzhanturkmen.myplantapp.ui.favorite

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oguzhanturkmen.myplantapp.data.models.Plant
import com.oguzhanturkmen.myplantapp.data.models.PlantFavModel
import com.oguzhanturkmen.myplantapp.data.repo.PlantRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(val plantRepo: PlantRepo) : ViewModel() {
    val favList = MutableLiveData<List<PlantFavModel>>()

    fun getAllFavPlants() {
        viewModelScope.launch {
            favList.value = plantRepo.loadFavPlants()
        }
    }

    fun deleteFromFav(favPlant:PlantFavModel) {
        viewModelScope.launch {
            plantRepo.deleteFromFav(favPlant)
            getAllFavPlants()
        }
    }
}