package it.polito.did.provanavgraph.ui.main

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import it.polito.did.provanavgraph.R
import it.polito.did.provanavgraph.repository.PlantRepository
import java.util.*
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class SinglePlantFragment : Fragment(R.layout.fragment_single_plant) {

    private lateinit var viewModel: PlantRepository
    private lateinit var plantName: TextView
    private lateinit var plantLocation: TextView
    private lateinit var plantSpecies: TextView
    private lateinit var plantHumidity: ProgressBar
    private lateinit var plantWaterInTank: ProgressBar
    private lateinit var plantImage: ImageView

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(PlantRepository::class.java)

        val backButton: Button = view.findViewById(R.id.frecciaBack)

        plantName = view.findViewById<TextView>(R.id.plantName)
        plantSpecies = view.findViewById<TextView>(R.id.plantSpecies)
        plantLocation = view.findViewById<TextView>(R.id.plantLocation)
        plantHumidity = view.findViewById<ProgressBar>(R.id.plantHumidity)
        plantWaterInTank = view.findViewById<ProgressBar>(R.id.plantWaterInTank)
        plantImage = view.findViewById<ImageView>(R.id.plantImage)

        val infoText = view.findViewById<TextView>(R.id.infoText)
        val infoIcon = view.findViewById<ImageView>(R.id.infoIcon)
        val infoTime = view.findViewById<TextView>(R.id.infoTimestamp)

        viewModel.setUserNotes()

        val liveData = viewModel.getPLants()
        val liveNote = viewModel.getUserNotes()

        liveData.observe(viewLifecycleOwner, androidx.lifecycle.Observer {

            plantName.text= liveData.value!!.get(viewModel.focusPlant).name
            plantSpecies.text = liveData.value!!.get(viewModel.focusPlant).species
            if (liveData.value!!.get(viewModel.focusPlant).isOutside) {
                plantLocation.text = "Outside"
            } else {
                plantLocation.text = "Inside"
            }

            plantHumidity.progress = liveData.value!!.get(viewModel.focusPlant).humidity
            plantWaterInTank.progress = liveData.value!!.get(viewModel.focusPlant).waterInTank


            when {
                liveData.value!!.get(viewModel.focusPlant).waterInTank > 70 ->
                    plantImage.setImageResource(R.drawable.happycactusnew)
                liveData.value!!.get(viewModel.focusPlant).waterInTank > 45 && liveData.value!!.get(viewModel.focusPlant).waterInTank <= 70 ->
                    plantImage.setImageResource(R.drawable.angrycactusnew)
                liveData.value!!.get(viewModel.focusPlant).waterInTank > 15 && liveData.value!!.get(viewModel.focusPlant).waterInTank <= 45 ->
                    plantImage.setImageResource(R.drawable.sadcactusnew)
                liveData.value!!.get(viewModel.focusPlant).waterInTank > 0 && liveData.value!!.get(viewModel.focusPlant).waterInTank <= 15 ->
                    plantImage.setImageResource(R.drawable.deadcactusnew)
                else -> {
                    plantImage.setImageResource(R.drawable.happycactusnew)
                }
            }
        })

        liveNote.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            var time: Long = 0
            var name: String = "null"
            var origin: String = "null"

            for (note in liveNote.value!!)
            {
                if(note.time.toLong() > time && note.name.equals(liveData.value!!.get(viewModel.focusPlant).key)){
                    time = note.time.toLong()
                    name = getPlantNameFromCode(note.name)
                    origin = note.origin
                }
            }

            infoTime.text = convertDate(time.toString())
            if (origin.equals("Water")){
                infoText.text = "Lefya ha bagnato " + name + " oggi!"
            }
            else{
                infoText.text = "L' acqua nel serbatoio di " + name + " sta finendo"
            }
        })



        backButton.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View?) {
                activity?.findNavController(R.id.fragmentContainerView5)?.navigate(R.id.action_singlePlantFragment_to_mainFragment)
            }

        })


    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun convertDate(dateInMilliseconds: String): String? {
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        val instant = Instant.ofEpochMilli(dateInMilliseconds.toLong())

        val date = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
        return formatter.format(date)
    }

    private fun getPlantNameFromCode (s: String) : String{
        var name: String = "null"
        for ( item in viewModel.plantList.value!!){
            if (item.key.equals(s)){
                name = item.name
            }
        }
        return name
    }

}