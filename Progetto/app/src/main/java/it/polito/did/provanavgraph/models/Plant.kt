package it.polito.did.provanavgraph.models

import android.icu.number.IntegerWidth

class Plant(plantName: String, plantSpecies: String, idNum: Int) {

    var name: String
    var species: String
    var id: Int

    init {
        name = plantName
        species = plantSpecies
        id=idNum
    }

    fun getPlantName(): String{
        return name
    }

    fun getPlantSpecies(): String{
        return species
    }

    fun getPlantId(): Int{
        return id
    }



}