package com.oguzhanturkmen.myplantapp.ui.profile

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.oguzhanturkmen.myplantapp.data.models.User
import com.oguzhanturkmen.myplantapp.data.repo.PlantRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    val plantRepo: PlantRepo,
    val firebaseAuth: FirebaseAuth,
    val firebaseStore: FirebaseStorage
) : ViewModel() {

    var userLive = MutableLiveData<User>()

    fun getLiveUser() {
        Log.d("deneme1", plantRepo.getLiveUser().value.toString())
        userLive = plantRepo.getLiveUser()
    }

    fun updateImage(imageUrl: String) {
        plantRepo.updateImage(imageUrl)
    }
}