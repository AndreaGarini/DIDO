package it.polito.did.provanavgraph.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.lifecycle.get
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import it.polito.did.provanavgraph.R
import it.polito.did.provanavgraph.models.Plant
import it.polito.did.provanavgraph.repository.PlantRepository
import java.util.stream.Collectors

class MainFragment : Fragment(R.layout.main_fragment) {

    companion object {
        fun newInstance() = MainFragment()
        fun newInstanceWithBundle(b: Bundle): MainFragment{
            val f = MainFragment();
            f.arguments = b
            return f
        }
    }


    private lateinit var viewModel: PlantRepository
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var list: MutableList<Plant> = mutableListOf()
        var db = Firebase.database.reference
        var parent = db.child("plants")
        var referenceToFragment: MainFragment= this
        viewModel = ViewModelProvider(requireActivity()).get(PlantRepository::class.java)


        val rv: RecyclerView= view.findViewById(R.id.recyclerView)
        rv.layoutManager= GridLayoutManager(activity, 2)
        rv.adapter= PlantAdapter(list, this, viewModel)

        parent.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                list.clear()
                viewModel.plantCounter=0
                for(item in snapshot.children) {
                    var plant = Plant(
                        item.child("plantName").value.toString(),
                        item.child("species").value.toString(),
                        viewModel.plantCounter,
                        item.child("category").value.toString(),
                        item.child("humidity").value.toString().toInt(),
                        item.child("waterInTank").value.toString().toInt(),
                        item.child("isOutside").value.toString().toBoolean()

                    )
                    viewModel.plantCount()
                    list.add(plant)
                }
                viewModel.plantList=list

                rv.adapter= PlantAdapter(list, referenceToFragment, viewModel)
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })

        viewModel = ViewModelProvider(requireActivity()).get(PlantRepository::class.java)


        }

    }

class PlantAdapter(val list: MutableList<Plant>, val fragment: MainFragment, val viewModel: PlantRepository): RecyclerView.Adapter<PlantAdapter.PlantViewHolder>(){


    class PlantViewHolder(v: View): RecyclerView.ViewHolder(v){
        val name1: TextView = v.findViewById(R.id.plantNameHome1)
        val species1: TextView = v.findViewById(R.id.plantSpeciesHome1)
        var plantButton1: ImageButton= v.findViewById(R.id.plantImageHome1)

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantViewHolder {
        val singleObject= LayoutInflater.from(parent.context).inflate(R.layout.plant_item_layout, parent, false)
        return PlantViewHolder(singleObject)
    }

    override fun onBindViewHolder(holder: PlantViewHolder, position: Int) {
        holder.name1.text= list.get(position).name
        holder.species1.text= list.get(position).species
       // holder.plantButton1.setImageDrawable( ) aggiungere riferimento all'imagine

        holder.plantButton1.setOnClickListener{
            viewModel.focusPlant=position
            fragment.findNavController().navigate(R.id.action_mainFragment_to_singlePlantFragment)
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }
}