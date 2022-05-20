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
import kotlinx.coroutines.newFixedThreadPoolContext

class PlantRepository: ViewModel() {

    var focusPlant: Int=0
    lateinit var plantOnFocus: Plant //pianta per i dati di pianta singola
    var plantCounter=0
    var user: String= "null"
    val db = Firebase.database.reference
    var plantList: MutableList<Plant> = mutableListOf()
    var userPlants: MutableList<String> = mutableListOf()
    var usersCount: Int=0

    val ref2 = db.child("users")

    val ref1 = db.child("plants")

    val usersRef= db.child("users").addValueEventListener(
        object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                 usersCount=snapshot.children.count()
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })



    fun plantCount(){
        plantCounter++
    }



}