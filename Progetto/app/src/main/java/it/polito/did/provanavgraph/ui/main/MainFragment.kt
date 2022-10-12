package it.polito.did.provanavgraph.ui.main

import android.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import it.polito.did.provanavgraph.R
import it.polito.did.provanavgraph.models.Plant
import it.polito.did.provanavgraph.repository.PlantRepository
import android.graphics.drawable.Drawable
import android.widget.*
import androidx.core.content.ContextCompat
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import it.polito.did.provanavgraph.HomeActivity
import pl.droidsonroids.gif.GifImageView


class MainFragment : Fragment(R.layout.main_fragment) {

    companion object {
        fun newInstance() = MainFragment()
        fun newInstanceWithBundle(b: Bundle): MainFragment {
            val f = MainFragment();
            f.arguments = b
            return f
        }
    }


    private lateinit var viewModel: PlantRepository
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var list: MutableList<Plant>? = mutableListOf()
        var db = Firebase.database.reference
        var parent = db.child("plants")
        var referenceToFragment: MainFragment = this
        viewModel = ViewModelProvider(requireActivity()).get(PlantRepository::class.java)

        val search: SearchView = view.findViewById(R.id.searchPlant)
        val textGarden: TextView = view.findViewById(R.id.textGarden)

        viewModel.setPlants()
        viewModel.setUserPlants()
        viewModel.setUserName()
        val liveName = viewModel.getUserName()
        liveName.observe(viewLifecycleOwner, Observer {
            textGarden.text = "Giardino di " + liveName.value
        })

       viewModel.db.child("notifications").get().addOnSuccessListener{
           for (note in it.children){
               Log.d("data", note.children.first().key.toString())
           }
       }
        val liveData = viewModel.getPLants()
        list = liveData.value


        val rv: RecyclerView = view.findViewById(R.id.recyclerView)
        rv.layoutManager = GridLayoutManager(activity, 2)
        liveData.observe(viewLifecycleOwner, Observer {
            list = liveData.value
            rv.adapter = PlantAdapter(list, referenceToFragment, viewModel)
        })
        rv.adapter = PlantAdapter(viewModel.plantList.value, referenceToFragment, viewModel)

        viewModel = ViewModelProvider(requireActivity()).get(PlantRepository::class.java)


        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {

                createShortList(newText)
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                createShortList(query)
                return false
            }

            fun createShortList(txt: String) {
                var shortList: MutableList<Plant>? = mutableListOf()

                if (txt != "") {
                    for (item in list!!) {
                        if (item.name.contains(txt, ignoreCase = true) || item.species.contains(txt, ignoreCase = true)) {
                                shortList!!.add(item)
                        }
                    }
                    shortList!!.add(Plant(
                        "plusButton",
                        "null",
                        "null",
                        "null",
                         viewModel.getPlantCount(),
                        "null",
                        0,
                        0,
                        false
                    ))
                    list = shortList
                    rv.adapter = PlantAdapter(list, referenceToFragment, viewModel)
                } else {
                    list = liveData.value
                    rv.adapter = PlantAdapter(list, referenceToFragment, viewModel)
                }
            }
        })
    }

    class PlantAdapter(
        val list: MutableList<Plant>?,
        val fragment: MainFragment,
        val viewModel: PlantRepository
    ) : RecyclerView.Adapter<PlantAdapter.PlantViewHolder>() {

        var currentSelectedIndex = -1

        class PlantViewHolder(v: View) : RecyclerView.ViewHolder(v) {
            val name1: TextView = v.findViewById(R.id.plantNameHome1)
            val species1: TextView = v.findViewById(R.id.plantSpeciesHome1)
            var plantButton1: ImageButton = v.findViewById(R.id.plantImageHome1)
            val constraintLayout: ConstraintLayout = v.findViewById(R.id.constraintLayout)
            var deletePlantButton: Button = v.findViewById(R.id.deletePlantButton)


        }


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantViewHolder {
            val singleObject = LayoutInflater.from(parent.context)
                .inflate(R.layout.plant_item_layout, parent, false)
            return PlantViewHolder(singleObject)
        }

        override fun onBindViewHolder(holder: PlantViewHolder, position: Int) {

            if (list != null) {
                if (position != list.size - 1) {
                    holder.name1.text = list.get(position).name
                    holder.species1.text = list.get(position).species

                    //dare dei dati sensati al water in tank
                    holder.plantButton1.setImageDrawable(fragment.activity?.let {
                        when {

                            list.get(position).waterInTank==0 && list.get(position).humidity ==0 ->
                                ContextCompat.getDrawable(it, R.drawable.dead_img)

                            list.get(position).waterInTank >= 75 && list.get(position).humidity >=75->
                                ContextCompat.getDrawable(it,R.drawable.happy_verdeacceso_img)
                            list.get(position).waterInTank >= 75 && list.get(position).humidity <75 && list.get(position).humidity >=50->
                                ContextCompat.getDrawable(it,R.drawable.happy_verdespento_img)
                            list.get(position).waterInTank >= 75 && list.get(position).humidity <50 && list.get(position).humidity >=25->
                                ContextCompat.getDrawable(it,R.drawable.happy_giallo_img)
                            list.get(position).waterInTank >= 75 && list.get(position).humidity <25 && list.get(position).humidity  >=0 ->
                                ContextCompat.getDrawable(it,R.drawable.happy_marroncino_img)

                            list.get(position).waterInTank < 75 &&list.get(position).waterInTank >=50 && list.get(position).humidity >=75->
                                ContextCompat.getDrawable(it,R.drawable.angry_verdeacceso_img)
                            list.get(position).waterInTank < 75 &&list.get(position).waterInTank >=50 && list.get(position).humidity <75 && list.get(position).humidity >=50->
                                ContextCompat.getDrawable(it,R.drawable.angry_verdespento_img)
                            list.get(position).waterInTank < 75 &&list.get(position).waterInTank >=50 && list.get(position).humidity <50 &&list.get(position).humidity >=25->
                                ContextCompat.getDrawable(it,R.drawable.angry_giallo_img)
                            list.get(position).waterInTank < 75 &&list.get(position).waterInTank >=50 && list.get(position).humidity <25 && list.get(position).humidity  >=0 ->
                                ContextCompat.getDrawable(it,R.drawable.angry_marroncino_img)

                            list.get(position).waterInTank < 50 &&list.get(position).waterInTank >=25 && list.get(position).humidity >=75->
                                ContextCompat.getDrawable(it,R.drawable.sad_verdeacceso_img)
                            list.get(position).waterInTank < 50 &&list.get(position).waterInTank >=25 && list.get(position).humidity <75 && list.get(position).humidity >=50->
                                ContextCompat.getDrawable(it,R.drawable.sad_verdespento_img)
                            list.get(position).waterInTank < 50 &&list.get(position).waterInTank >=25 && list.get(position).humidity <50 && list.get(position).humidity >=25->
                                ContextCompat.getDrawable(it,R.drawable.sad_giallo_img)
                            list.get(position).waterInTank < 50 &&list.get(position).waterInTank >=25 && list.get(position).humidity <25 && list.get(position).humidity  >=0 ->
                                ContextCompat.getDrawable(it,R.drawable.sad_marroncino_img)

                            list.get(position).waterInTank >=0 && list.get(position).waterInTank < 25 &&  list.get(position).humidity >=75->
                                ContextCompat.getDrawable(it,R.drawable.dejected_verdeacceso_img)
                            list.get(position).waterInTank >=0 && list.get(position).waterInTank <25 &&  list.get(position).humidity  <75 && list.get(position).humidity  >=50->
                                ContextCompat.getDrawable(it,R.drawable.dejected_verdespento_img)
                            list.get(position).waterInTank >=0 &&list.get(position).waterInTank <25 &&  list.get(position).humidity  <50 && list.get(position).humidity  >=25->
                                ContextCompat.getDrawable(it,R.drawable.dejected_giallo_img)
                            list.get(position).waterInTank >=0  && list.get(position).waterInTank <25 &&  list.get(position).humidity  <25 && list.get(position).humidity  >=0 ->
                                ContextCompat.getDrawable(it,R.drawable.dejected_marroncino_img)

                         /*  list.get(position).waterInTank==0 && list.get(position).humidity <1 ->
                                ContextCompat.getDrawable(it, R.drawable.dead_img)
                            list.get(position).waterInTank >=0 && list.get(position).humidity ==0 ->
                                ContextCompat.getDrawable(it, R.drawable.dead_img)*/



                            else -> {
                                ContextCompat.getDrawable(it, R.drawable.happycactusnew)
                            }
                        }
                    })

                    holder.plantButton1.setOnClickListener {
                        viewModel.focusPlant = position
                        fragment.findNavController()
                            .navigate(R.id.action_mainFragment_to_singlePlantFragment)
                    }

                    if (list.get(position).selected == true) {
                        holder.deletePlantButton.visibility = View.VISIBLE
                        holder.deletePlantButton.setOnClickListener {
                            val builder = AlertDialog.Builder(fragment.requireActivity())
                            builder.setMessage(
                                "Are you sure you want to delete " + list.get(
                                    position
                                ).name + "?"
                            )
                                .setCancelable(false)
                                .setPositiveButton("Yes") { dialog, id ->


                                    deletePlant(position)
                                    deletePlantNotes(position)
                                }
                                .setNegativeButton("No") { dialog, id ->
                                    dialog.dismiss()
                                }
                            val alert = builder.create()
                            alert.show()
                        }

                    } else {
                        holder.deletePlantButton.visibility = View.GONE
                    }
                    //qui implementato il tenere premuto che fa apparire il bidone per eliminare
                    holder.constraintLayout.setOnLongClickListener { markSelectedItem(position) }
                } else {
                    holder.name1.text = null
                    holder.species1.text = null
                    holder.deletePlantButton.visibility = View.INVISIBLE
                    val uri =
                        "@drawable/ic_add_plant" // where myresource (without the extension) is the file
                    val imageResource: Int = fragment.resources.getIdentifier(
                        uri,
                        null,
                        fragment.requireActivity().packageName
                    )
                    var res: Drawable = fragment.resources.getDrawable(imageResource)
                    holder.plantButton1.setImageDrawable(res)
                    holder.name1.text = "Aggiungi"
                    holder.species1.text = "pianta"
                    holder.plantButton1.setOnClickListener {
                        (fragment.activity as HomeActivity).hideFooter()
                        fragment.findNavController()
                            .navigate(R.id.action_mainFragment_to_creazione_pianta)
                    }
                }
            }
        }

        override fun getItemCount(): Int {
            if (list != null) {
                return list.size
            } else return 0
        }

        fun markSelectedItem(index: Int): Boolean {
            if (list != null) {
                for (item in list) {
                    item.selected = false;
                }

                list.get(index).selected = true
                currentSelectedIndex = index
                // TODO: 07/10/22 forse è qui p'errore che fa fare il crash se ellimini tutte le piante
                notifyDataSetChanged()

                return true
            } else return false
        }

        fun deletePlant(index: Int) {
            if (list != null) {
                var db = Firebase.database.reference
                var pianta: Plant = list.get(index)
                var chiave: String = pianta.getPlantKey()
                var utente: String = pianta.getPlantOwner()
                db.child("plants").child(chiave).removeValue()
                db.child("users").child(utente).child("ownedPlants")
                    .child(chiave).removeValue()
            }
        }

        fun deletePlantNotes(index: Int){
            if (list != null) {
                var db = Firebase.database.reference
                var pianta: Plant = list.get(index)
                var chiave: String = pianta.getPlantKey()
                viewModel.db.child("notifications").get().addOnSuccessListener{
                    for (note in it.children){
                        if (note.children.first().key.toString().equals(chiave)){
                            viewModel.db.child("notifications").child(note.key.toString()).removeValue()
                        }
                    }
                }

            }
        }

    }
}
