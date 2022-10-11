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
import it.polito.did.provanavgraph.models.Note
import it.polito.did.provanavgraph.models.Plant
import kotlinx.coroutines.newFixedThreadPoolContext

class PlantRepository: ViewModel() {

    var focusPlant: Int=0
    lateinit var plantOnFocus: Plant //pianta per i dati di pianta singola
    var plantCounter=0
    var user: String= "null"
    var userDB: String= "null"
    val db = Firebase.database.reference
    var plantList: MutableLiveData<MutableList<Plant>> = MutableLiveData()
    var notes: MutableLiveData<MutableList<Note>> = MutableLiveData()
    var userPlants: MutableLiveData<MutableList<String>> = MutableLiveData()
    var usersNum: Int=0
    var plantsNum: Int=0
    var users: MutableLiveData<Map<String, String>> = MutableLiveData()
    var unicode: MutableLiveData<Long> = MutableLiveData()
    var userName: MutableLiveData<String> = MutableLiveData()
    var userKey: MutableLiveData<String> = MutableLiveData()
    var userPass: MutableLiveData<String> = MutableLiveData()
    var desHum: MutableLiveData<Map<String, Long>> = MutableLiveData()
    var openTimestamp: Double = 0.0


    fun newTimestamp(){
        openTimestamp = System.currentTimeMillis().toDouble()
    }

    fun setDesHum(){
        db.child("plantCategories").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var map: MutableMap<String, Long> = mutableMapOf()
               for (cat in snapshot.children){
                   Log.d ("cat: ", cat.toString())
                   for (species in cat.children){
                       var hum = species.child("desiredHumidity").value as Long
                       var spc = species.child("name").value as String
                       map.put(spc, hum)
                   }
               }
                desHum.value = map
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }



    fun getDes() : MutableLiveData<Map<String, Long>>{
        return desHum
    }

    fun setUnicode(){
        db.child("unicode").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                unicode.value = snapshot.value as Long
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    fun getUni() : MutableLiveData<Long>{
        return unicode
    }

    fun setUserName(){
        db.child("users").orderByChild("email").equalTo(user).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                userName.value = snapshot.children.first().child("username").value.toString()
                userPass.value = snapshot.children.first().child("password").value.toString()
                userKey.value = snapshot.children.first().key.toString()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    fun getUserName(): LiveData<String> {
        return userName
    }

    fun getUserKey(): LiveData<String> {
        return userKey
    }


    fun getUserPass(): LiveData<String> {
        return userPass
    }


    fun usersCount(): Int {
        return usersNum
    }

    fun getPlantCount(): Int{
        return plantsNum
    }

    fun plantCount(){
        plantCounter++
    }


    fun setPlants(){
        db.child("plants").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var list: MutableList<Plant> = mutableListOf()
                plantCounter=0
                plantsNum=snapshot.childrenCount.toInt()
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
                userDB=snapshot.children.first().key.toString()
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

    fun setUserNotes(){
        db.child("notifications").orderByKey().addValueEventListener(object : ValueEventListener
        {
            override fun onDataChange(snapshot: DataSnapshot) {
                var noteList: MutableList<Note> = mutableListOf()
                for(item in snapshot.children){
                    if(userPlants.value!!.contains(item.children.first().key as String)){
                       var note = Note(item.key.toString(), item.children.first().value.toString(), item.children.first().key.toString())
                        noteList.add(note)
                    }
                }
                notes.value = noteList
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        }


        )

    }

    fun getUserNotes() : LiveData<MutableList<Note>>{
        return notes
    }


}

