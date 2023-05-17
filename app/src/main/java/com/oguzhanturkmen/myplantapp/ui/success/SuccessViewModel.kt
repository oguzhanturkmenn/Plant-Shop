package com.oguzhanturkmen.myplantapp.ui.success

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oguzhanturkmen.myplantapp.data.models.Plant
import com.oguzhanturkmen.myplantapp.data.repo.PlantRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SuccessViewModel @Inject constructor(var repo: PlantRepo) : ViewModel() {

    val readAllBasket: LiveData<List<Plant>> = repo.readAllBasket

    fun deleteFromBasket(basketId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.deleteFromBasket(basketId)
        }
    }
}