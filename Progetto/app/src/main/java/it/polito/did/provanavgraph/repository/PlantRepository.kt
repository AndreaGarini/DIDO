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
    var userName: MutableLiveData<String> = MutableLiveData()


    val usersRef= db.child("users").addValueEventListener(object : ValueEventListener
    {
        override fun onDataChange(snapshot: DataSnapshot) {
          usersNum= snapshot.childrenCount.toInt()
        }

        override fun onCancelled(error: DatabaseError) {
            TODO("Not yet implemented")
        }

    })

    fun setUserName(){
        db.child("users").orderByChild("email").equalTo(user).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                userName.value = snapshot.children.first().child("username").value.toString()
                Log.d("snapshot child: ", snapshot.children.first().child("username").value.toString())
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    fun getUserName(): LiveData<String> {
        return userName
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
                    if(userPlants.value!!.contains(item.children.first().key.toString())){
                       var note = Note(item.key.toString(), item.children.first().value.toString(), item.children.first().key.toString())
                        noteList.add(note)
                    }
                }
                notes.value = noteList
                Log.d("userNotes", noteList.first().name)
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

