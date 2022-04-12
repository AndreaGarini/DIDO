package it.polito.did.provanavgraph.repository

import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import it.polito.did.provanavgraph.models.Plant

class PlantRepository {
    var focusPlant: Int=0
    val db = Firebase.database.reference
    val plantList = MutableLiveData<Plant>()


    val ref1 = db.child("plants")

}