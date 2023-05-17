package com.oguzhanturkmen.myplantapp.ui.plantdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oguzhanturkmen.myplantapp.data.models.Plant
import com.oguzhanturkmen.myplantapp.data.repo.PlantRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlantDetailViewModel @Inject constructor(var plantRepo: PlantRepo) : ViewModel() {

    fun addToBasket(plant: Plant) {
        viewModelScope.launch {
            plantRepo.addToBasket(plant)
        }
    }
}