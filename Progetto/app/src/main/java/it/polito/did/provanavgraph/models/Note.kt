package it.polito.did.provanavgraph.models

import android.icu.number.IntegerWidth
import java.sql.Timestamp

class Note(timestampKey: String, type: String, plantName: String) {

    var time: String
    var origin: String
    var name: String


    init {
        time = timestampKey
        origin = type
        name = plantName
    }

    fun getNoteTime(): String{
        return time
    }

    fun getNoteOrigin(): String{
        return origin
    }

    fun getNotePlant (): String{
        return name
    }

}