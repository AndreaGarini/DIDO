package it.polito.did.provanavgraph.models

import android.icu.number.IntegerWidth

class Plant(plantName: String, plantSpecies: String) {

    var name: String
    var species: String

    init {
        name = plantName
        species = plantSpecies
    }

    fun getPlantName(): String{
        return name
    }

    fun getPlantSpecies(): String{
        return species
    }



}