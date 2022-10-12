package it.polito.did.provanavgraph.ui.main

import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import it.polito.did.provanavgraph.HomeActivity
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
        val infoZone = view.findViewById<ConstraintLayout>(R.id.infoSinglePlant)
        val changeName = view.findViewById<TextView>(R.id.changeNameSingle)
        val changeIcon = view.findViewById<ImageView>(R.id.changeNameIcon)


        var buttonChangeNameStatus : String = "change"

        viewModel.setUserNotes()

        val liveData = viewModel.getPLants()
        val liveNote = viewModel.getUserNotes()



        infoZone.setOnClickListener {
            findNavController().navigate(R.id.action_singlePlantFragment_to_messageFragment)
            (activity as HomeActivity).notesNum.text = ""
            viewModel.newTimestamp()
        }

        changeIcon.setOnClickListener {
            when(buttonChangeNameStatus){
                "change" -> {
                    plantName.visibility = View.INVISIBLE
                    changeName.visibility = View.VISIBLE
                    buttonChangeNameStatus = "confirm"
                    changeIcon.setImageDrawable(resources.getDrawable(R.drawable.ic_baseline_done_all_24))
                }
                "confirm" -> {
                    viewModel.db.child("plants").child(liveData.value!!.get(viewModel.focusPlant).key).child("plantName").setValue(changeName.text.toString())
                    plantName.text = changeName.text.toString()
                    plantName.visibility = View.VISIBLE
                    changeName.visibility = View.INVISIBLE
                    buttonChangeNameStatus = "change"
                    changeIcon.setImageDrawable(resources.getDrawable(R.drawable.ic_pen))
                }
            }
        }


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

                liveData.value!!.get(viewModel.focusPlant).waterInTank==0 && liveData.value!!.get(viewModel.focusPlant).humidity==0 ->
                    plantImage.setImageResource(R.drawable.dead)

                liveData.value!!.get(viewModel.focusPlant).waterInTank >= 75 && liveData.value!!.get(viewModel.focusPlant).humidity >=75->
                    plantImage.setImageResource(R.drawable.happy_verdeacceso)
                liveData.value!!.get(viewModel.focusPlant).waterInTank >= 75 && liveData.value!!.get(viewModel.focusPlant).humidity <75 && liveData.value!!.get(viewModel.focusPlant).humidity >=50->
                    plantImage.setImageResource(R.drawable.happy_verdespento)
                liveData.value!!.get(viewModel.focusPlant).waterInTank >= 75 && liveData.value!!.get(viewModel.focusPlant).humidity <50 && liveData.value!!.get(viewModel.focusPlant).humidity >=25->
                    plantImage.setImageResource(R.drawable.happy_giallo)
                liveData.value!!.get(viewModel.focusPlant).waterInTank >= 75  && liveData.value!!.get(viewModel.focusPlant).humidity >=0 && liveData.value!!.get(viewModel.focusPlant).humidity <25 ->
                    plantImage.setImageResource(R.drawable.happy_marroncino)

                liveData.value!!.get(viewModel.focusPlant).waterInTank < 75 &&liveData.value!!.get(viewModel.focusPlant).waterInTank >=50 && liveData.value!!.get(viewModel.focusPlant).humidity >=75->
                    plantImage.setImageResource(R.drawable.angry_verdeacceso)
                liveData.value!!.get(viewModel.focusPlant).waterInTank < 75 &&liveData.value!!.get(viewModel.focusPlant).waterInTank >=50 && liveData.value!!.get(viewModel.focusPlant).humidity <75 && liveData.value!!.get(viewModel.focusPlant).humidity >=50->
                    plantImage.setImageResource(R.drawable.angry_verdespento)
                liveData.value!!.get(viewModel.focusPlant).waterInTank < 75 &&liveData.value!!.get(viewModel.focusPlant).waterInTank >=50 && liveData.value!!.get(viewModel.focusPlant).humidity <50 && liveData.value!!.get(viewModel.focusPlant).humidity >=25->
                    plantImage.setImageResource(R.drawable.angry_giallo)
                liveData.value!!.get(viewModel.focusPlant).waterInTank < 75 &&liveData.value!!.get(viewModel.focusPlant).waterInTank >=50  && liveData.value!!.get(viewModel.focusPlant).humidity >=0 && liveData.value!!.get(viewModel.focusPlant).humidity <25 ->
                    plantImage.setImageResource(R.drawable.angry_marroncino)

                liveData.value!!.get(viewModel.focusPlant).waterInTank < 50 &&liveData.value!!.get(viewModel.focusPlant).waterInTank >=25 && liveData.value!!.get(viewModel.focusPlant).humidity >=75->
                    plantImage.setImageResource(R.drawable.sad_verdeacceso)
                liveData.value!!.get(viewModel.focusPlant).waterInTank < 50 &&liveData.value!!.get(viewModel.focusPlant).waterInTank >=25 && liveData.value!!.get(viewModel.focusPlant).humidity <75 && liveData.value!!.get(viewModel.focusPlant).humidity >=50->
                    plantImage.setImageResource(R.drawable.sad_verdespento)
                liveData.value!!.get(viewModel.focusPlant).waterInTank < 50 &&liveData.value!!.get(viewModel.focusPlant).waterInTank >=25 && liveData.value!!.get(viewModel.focusPlant).humidity <50 && liveData.value!!.get(viewModel.focusPlant).humidity >=25->
                    plantImage.setImageResource(R.drawable.sad_giallo)
                liveData.value!!.get(viewModel.focusPlant).waterInTank < 50 &&liveData.value!!.get(viewModel.focusPlant).waterInTank >=25  && liveData.value!!.get(viewModel.focusPlant).humidity >=0 && liveData.value!!.get(viewModel.focusPlant).humidity <25 ->
                    plantImage.setImageResource(R.drawable.sad_marroncino)

                liveData.value!!.get(viewModel.focusPlant).waterInTank >=0 && liveData.value!!.get(viewModel.focusPlant).waterInTank < 25 && liveData.value!!.get(viewModel.focusPlant).humidity >=75->
                    plantImage.setImageResource(R.drawable.dejected_verdeacceso)
                liveData.value!!.get(viewModel.focusPlant).waterInTank >=0 &&  liveData.value!!.get(viewModel.focusPlant).waterInTank <25 && liveData.value!!.get(viewModel.focusPlant).humidity <75 && liveData.value!!.get(viewModel.focusPlant).humidity >=50->
                    plantImage.setImageResource(R.drawable.dejected_verdespento)
                liveData.value!!.get(viewModel.focusPlant).waterInTank >=0 &&  liveData.value!!.get(viewModel.focusPlant).waterInTank <25 && liveData.value!!.get(viewModel.focusPlant).humidity <50 && liveData.value!!.get(viewModel.focusPlant).humidity >=25->
                    plantImage.setImageResource(R.drawable.dejected_giallo)
                liveData.value!!.get(viewModel.focusPlant).waterInTank >=0 &&  liveData.value!!.get(viewModel.focusPlant).waterInTank <25 && liveData.value!!.get(viewModel.focusPlant).waterInTank >=0 && liveData.value!!.get(viewModel.focusPlant).humidity >=0 && liveData.value!!.get(viewModel.focusPlant).humidity <25   ->
                    plantImage.setImageResource(R.drawable.dejected_marroncino)

                else -> {
                    plantImage.setImageResource(R.drawable.happycactusnew)
                }
            }
        })

        liveNote.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            var time: Double = 0.0
            var name: String = "null"
            var origin: String = "null"

            for (note in liveNote.value!!)
            {
                if(note.time.toDouble() > time && note.name.equals(liveData.value!!.get(viewModel.focusPlant).key)){
                    time = note.time.toDouble()
                    name = getPlantNameFromCode(note.name)
                    origin = note.origin
                }
            }

            if (name!= "null"){
                infoTime.text = convertDate(time.toString())
                if (origin.equals("Water")){
                    infoText.text = "Lefya ha bagnato " + name + " oggi!"
                    infoIcon.setImageDrawable(activity?.let {
                        ContextCompat.getDrawable(
                            it, R.drawable.ic_baseline_bubble_chart_24)
                    })
                }
                else {
                    infoText.text = "L' acqua nel serbatoio di " + name + " sta finendo"
                    infoIcon.setImageDrawable(activity?.let {
                        ContextCompat.getDrawable(
                            it, R.drawable.ic_baseline_water_damage_24)
                    })
                }
            }
            else{
                infoTime.text = ""
                infoText.text = "leafya sorveglia. passo e chiudo."
                infoIcon.setImageDrawable(activity?.let {
                    ContextCompat.getDrawable(
                        it, R.drawable.ic_baseline_autorenew_24)
                })
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
        val num = dateInMilliseconds.toDouble()
        val instant = Instant.ofEpochMilli(num.toLong())

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