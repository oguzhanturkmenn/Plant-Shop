package com.oguzhanturkmen.myplantapp.ui.signup

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.oguzhanturkmen.myplantapp.data.models.Answer
import com.oguzhanturkmen.myplantapp.data.repo.PlantRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    val plantRepo: PlantRepo
) : ViewModel() {
    var answer = MutableLiveData<Answer>()

    fun register(userEmail: String, userPassword: String) {
        answer = plantRepo.register(userEmail, userPassword)
    }
}