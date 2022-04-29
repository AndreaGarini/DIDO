package it.polito.did.provanavgraph.models

import android.icu.number.IntegerWidth

class Plant(plantName: String, plantSpecies: String, idNum: Int, plantCategory: String, plantHumidity: Int, plantWaterInTank: Int, plantIsOutside: Boolean) {

    var name: String
    var species: String
    var id: Int
    var category: String
    var humidity: Int
    var waterInTank: Int
    var isOutside: Boolean


    init {
        name = plantName
        species = plantSpecies
        id=idNum
        category= plantCategory
        humidity= plantHumidity
        waterInTank= plantWaterInTank
        isOutside= plantIsOutside

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

    fun getPlantCategory(): String{
        return category
    }

    fun getPlantHumidity(): Int{
        return humidity
    }

    fun getPlantWaterInTank(): Int{
        return waterInTank
    }

    fun getPlantIsOutside(): Boolean{
        return isOutside
    }


}