package it.polito.did.provanavgraph.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import it.polito.did.provanavgraph.R
import it.polito.did.provanavgraph.repository.PlantRepository

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class SinglePlantFragment : Fragment(R.layout.fragment_single_plant) {

    private lateinit var viewModel: PlantRepository

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(PlantRepository::class.java)


        var plantButton = view.findViewById<Button>(R.id.singlePlantButton)
        var plantName=view.findViewById<TextView>(R.id.plantName)
        var plantSpecies=view.findViewById<TextView>(R.id.plantSpecies)
        var plantLocation=view.findViewById<TextView>(R.id.plantLocation)
        var plantHumidity=view.findViewById<ProgressBar>(R.id.plantHumidity)
        var plantWaterInTank=view.findViewById<ProgressBar>(R.id.plantWaterInTank)

        plantName.text= viewModel.plantList[viewModel.focusPlant].name
        plantSpecies.text= viewModel.plantList[viewModel.focusPlant].species
        if(viewModel.plantList[viewModel.focusPlant].isOutside){
            plantLocation.text="Outside"
        }else{
            plantLocation.text="Inside"
        }

        plantHumidity.progress=viewModel.plantList[viewModel.focusPlant].humidity
        plantWaterInTank.progress=viewModel.plantList[viewModel.focusPlant].waterInTank
    }
}