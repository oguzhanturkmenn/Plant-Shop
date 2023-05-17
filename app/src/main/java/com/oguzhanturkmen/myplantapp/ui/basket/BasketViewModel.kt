package com.oguzhanturkmen.myplantapp.ui.basket

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oguzhanturkmen.myplantapp.data.models.Plant
import com.oguzhanturkmen.myplantapp.data.repo.PlantRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BasketViewModel @Inject constructor(var plantRepo: PlantRepo) : ViewModel() {
    val readAllBasket: LiveData<List<Plant>> = plantRepo.readAllBasket

    private val _totalAmount = MutableLiveData(0)
    val totalAmount: LiveData<Int> = _totalAmount

    fun deleteFromBasket(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            plantRepo.deleteFromBasket(name)
        }
    }

    private fun updateBasket(plant: Plant) {
        viewModelScope.launch(Dispatchers.IO) {
            plantRepo.updateBasket(plant)
        }
    }

    fun increase(plant: Plant) {
        _totalAmount.value = _totalAmount.value?.plus(plant.plantPrice)
        updateBasket(plant)
    }

    fun decrease(plant: Plant) {
        _totalAmount.value = _totalAmount.value?.minus(plant.plantPrice)
        updateBasket(plant)
    }

    fun totalBasket() {
        viewModelScope.launch() {
            _totalAmount.value = plantRepo.getTotalPrice()
        }
    }

}