package it.polito.did.provanavgraph.ui.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import it.polito.did.provanavgraph.R
import it.polito.did.provanavgraph.repository.PlantRepository
import java.util.*

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(PlantRepository::class.java)


        plantName = view.findViewById<TextView>(R.id.plantName)
        plantSpecies = view.findViewById<TextView>(R.id.plantSpecies)
        plantLocation = view.findViewById<TextView>(R.id.plantLocation)
        plantHumidity = view.findViewById<ProgressBar>(R.id.plantHumidity)
        plantWaterInTank = view.findViewById<ProgressBar>(R.id.plantWaterInTank)
        plantImage = view.findViewById<ImageView>(R.id.plantImage)

        val liveData = viewModel.getPLants()

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

            if (liveData.value!!.get(viewModel.focusPlant).humidity > 50) {
                plantImage.setImageResource(R.drawable.happycactus)
            } else {
                plantImage.setImageResource(R.drawable.angrycactus)
            }

        })
/*
            plantName.text = viewModel.plantList.[viewModel.focusPlant]?.name
            plantSpecies.text = viewModel.plantList[viewModel.focusPlant].species
            if (viewModel.plantList[viewModel.focusPlant].isOutside) {
                plantLocation.text = "Outside"
            } else {
                plantLocation.text = "Inside"
            }

            viewModel.plantList[viewModel.focusPlant].humidity
            plantHumidity.progress = viewModel.plantList[viewModel.focusPlant].humidity
            plantWaterInTank.progress = viewModel.plantList[viewModel.focusPlant].waterInTank

            if (viewModel.plantList[viewModel.focusPlant].humidity > 50) {
                plantImage.setImageResource(R.drawable.happycactus)
            } else {
                plantImage.setImageResource(R.drawable.angrycactus)
            }*/


    }

}