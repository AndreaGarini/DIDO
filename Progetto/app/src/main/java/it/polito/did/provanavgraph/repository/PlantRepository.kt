package it.polito.did.provanavgraph.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import it.polito.did.provanavgraph.models.Plant
import it.polito.did.provanavgraph.ui.main.PlantAdapter
import kotlinx.coroutines.newFixedThreadPoolContext

class PlantRepository: ViewModel() {

    var focusPlant: Int=0
    lateinit var plantOnFocus: Plant //pianta per i dati di pianta singola
    var plantCounter=0
    var user: String= "null"
    val db = Firebase.database.reference
    var plantList: MutableLiveData<MutableList<Plant>> = MutableLiveData()
    var userPlants: MutableLiveData<MutableList<String>> = MutableLiveData()
    var usersCount: Int=0


    fun plantCount(){
        plantCounter++
    }


    fun setPlants(){
        db.child("plants").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var list: MutableList<Plant> = mutableListOf()
                plantCounter=0
                for(item in snapshot.children) {
                    if (userPlants.value!!.contains(item.key.toString())) {
                        var plant = Plant(
                            item.key.toString(),
                            item.child("owner").value.toString(),
                            item.child("plantName").value.toString(),
                            item.child("species").value.toString(),
                            plantCounter,
                            item.child("category").value.toString(),
                            item.child("humidity").value.toString().toInt(),
                            item.child("waterInTank").value.toString().toInt(),
                            item.child("isOutside").value.toString().toBoolean()

                        )
                        Log.d("new humidity", plant.humidity.toString())
                        plantCount()
                        list.add(plant)
                    }
                }
                list.add(Plant(
                    "plusButton",
                   "null",
                    "null",
                    "null",
                    plantCounter,
                    "null",
                    0,
                    0,
                    false
                ))
                plantList.value=list
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    fun getPLants(): LiveData<MutableList<Plant>>{
        return plantList
    }

    fun setUserPlants(){
        db.child("users").orderByChild("email").equalTo(user).addValueEventListener(object : ValueEventListener
        {
            override fun onDataChange(snapshot: DataSnapshot) {
                var list: MutableList<String> = mutableListOf()
                for(item in snapshot.children.first().children){
                    if(item.key=="ownedPlants"){
                        for(plant in item.children){
                            list.add(plant.key.toString())
                        }
                        userPlants.value=list

                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        }


        )
    }

    fun getUserPlants() : LiveData<MutableList<String>>{
        return userPlants
    }



}

