package it.polito.did.provanavgraph.repository

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

class PlantRepository: ViewModel() {

    var focusPlant: Int=0
    var plantCounter=0
    val db = Firebase.database.reference
    var plantList: MutableList<Plant> = mutableListOf()


    val ref1 = db.child("plants")
    val ref2= db.child("users")

    fun plantCount(){
        plantCounter++
    }



}