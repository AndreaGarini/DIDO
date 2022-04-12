package it.polito.did.provanavgraph.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import it.polito.did.provanavgraph.Plant
import it.polito.did.provanavgraph.R
import kotlin.math.log

class MainFragment : Fragment(R.layout.main_fragment) {

    companion object {
        fun newInstance() = MainFragment()
        fun newInstanceWithBundle(b: Bundle): MainFragment{
            val f = MainFragment();
            f.arguments = b
            return f
        }
    }


    private lateinit var viewModel: MainViewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val nome1 = view.findViewById<Button>(R.id.pianta1)
        val nome2 = view.findViewById<Button>(R.id.pianta2)
        var list: MutableList<Plant> = mutableListOf()
        val db = Firebase.database.reference



        val rv: RecyclerView= view.findViewById(R.id.recyclerView)
        rv.layoutManager= LinearLayoutManager(this.activity)
        rv.adapter= PlantAdapter(list)





        val ref1 = db.child("message1")
        val ref2 =db.child("message2")
        var parent = db.child("plants")

        parent.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                for(item in snapshot.children){

                    var plant= Plant(item.child("plantName").value.toString(), item.child("spieces").value.toString())
                    list.add(plant)

                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
        ref1.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                nome1.text = snapshot.getValue<String>()
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })

        ref2.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                nome2.text = snapshot.getValue<String>()
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)





        nome1.setOnClickListener{
            findNavController().navigate(R.id.action_mainFragment_to_singlePlantFragment)
        }


        }

    }

class PlantAdapter(val list: MutableList<Plant>): RecyclerView.Adapter<PlantAdapter.PlantViewHolder>(){

    class PlantViewHolder(v: View): RecyclerView.ViewHolder(v){
        val name: TextView = v.findViewById(R.id.plantNameHome)

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantViewHolder {
        val singleObject= LayoutInflater.from(parent.context).inflate(R.layout.plant_item_layout, parent, false)
        return PlantViewHolder(singleObject)
    }

    override fun onBindViewHolder(holder: PlantViewHolder, position: Int) {
        holder.name.text= list.get(position).name
    }

    override fun getItemCount(): Int {
        return list.size
    }
}