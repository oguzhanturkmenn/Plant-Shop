package com.oguzhanturkmen.myplantapp.data.datasource

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.CollectionReference
import com.oguzhanturkmen.myplantapp.data.models.Answer
import com.oguzhanturkmen.myplantapp.data.models.Plant
import com.oguzhanturkmen.myplantapp.data.models.PlantFavModel
import com.oguzhanturkmen.myplantapp.data.models.User
import com.oguzhanturkmen.myplantapp.retrofit.PlantAPI
import com.oguzhanturkmen.myplantapp.room.PlantDao
import com.oguzhanturkmen.myplantapp.room.PlantDatabase
import com.oguzhanturkmen.myplantapp.room.PlantFavDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PlantDataSource(
    val firebaseAuth: FirebaseAuth,
    private val collectionReference: CollectionReference,
    private val plantAPI: PlantAPI,
    val plantDao: PlantDao,
    val favDao: PlantFavDao
    ) {
    private var allPlantList = listOf<Plant>()
    private val answerLogin = MutableLiveData<Answer>()
    private val answerRegister = MutableLiveData<Answer>()
    private val userLiveData = MutableLiveData<User>()
    private var userId: String? = firebaseAuth.currentUser?.uid


    suspend fun getAllPlants(): List<Plant> {
            withContext(Dispatchers.IO) {
                allPlantList = plantAPI.getPlant().data.mapIndexed { index, plant -> plant.copy(id = index) }
            }

        return allPlantList
    }

    suspend fun getTotalPrice() = plantDao.getTotalPrice()

    suspend fun updateBasket(plant: Plant) = plantDao.updateBasket(plant)

    suspend fun deleteFromBasket(name:String) = plantDao.deleteFromBasket(name)

    suspend fun addToBasket(plant: Plant) {
        withContext(Dispatchers.IO) {
            plantDao.saveBasket(plant)
        }
    }

    //FAVORITE
    suspend fun loadFavPlants(): List<PlantFavModel> = favDao.getAllFav()

    suspend fun addToFav(favPlant: PlantFavModel) = favDao.addToFav(favPlant)

    suspend fun deleteFromFav(favPlant:PlantFavModel) = favDao.deleteFromFav(favPlant)

    //FIREBASE
    fun login(email: String, password: String): MutableLiveData<Answer> {
        answerLogin.value = Answer(null, null)
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                answerLogin.value = Answer(1, "")
            }
        }.addOnFailureListener {
            answerLogin.value = Answer(0, it.message)
        }
        return answerLogin
    }

    fun register(email: String, password: String): MutableLiveData<Answer> {
        answerRegister.value = Answer(null, null)
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val firebaseUser: FirebaseUser = task.result!!.user!!
                    val uid = firebaseUser.uid
                    val user = User(uid, email)
                    collectionReference.document(uid).set(user).addOnSuccessListener {
                        answerRegister.value = Answer(1, "")
                    }
                }
            }.addOnFailureListener {
                answerRegister.value = Answer(0, it.message)
            }
        return answerRegister
    }

    fun getLiveUser(): MutableLiveData<User> {
        userId = firebaseAuth.currentUser?.uid
        userId?.let {
            collectionReference.document(it).addSnapshotListener { value, _ ->
                if (value != null) {
                    val hashmap: HashMap<String, Any?> = value.data as HashMap<String, Any?>
                    val liveUser = User(
                        hashmap["userId"].toString(),
                        hashmap["userEmail"].toString(),
                        hashmap["ppUrl"].toString()
                    )
                    Log.d("denemesource", liveUser.toString())
                    userLiveData.value = liveUser
                }
            }
        }
        //Return the MutableLiveData object directly, without wrapping it in another MutableLiveData object
        return userLiveData
    }

    fun updateImage(imageUrl: String) {
        val image = HashMap<String, Any>()
        image["ppUrl"] = imageUrl
        userId?.let {
            collectionReference.document(it).update(image)
        }
    }

}

