package it.polito.did.provanavgraph.ui.main

import android.app.AlertDialog
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import it.polito.did.provanavgraph.R
import it.polito.did.provanavgraph.models.Note
import it.polito.did.provanavgraph.models.Plant
import it.polito.did.provanavgraph.repository.PlantRepository
import java.text.SimpleDateFormat
import java.util.*


class MessageFragment : Fragment(R.layout.fragment_message) {
    private lateinit var viewModel: PlantRepository
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(PlantRepository::class.java)

        viewModel.setUserNotes()
        val liveData = viewModel.getUserNotes()
        var referenceToFragment: MessageFragment= this

        val rv: RecyclerView = view.findViewById(R.id.noteRecyclerView)
        rv.layoutManager = LinearLayoutManager(activity)
        liveData.observe(viewLifecycleOwner, Observer {
            rv.adapter = NoteAdapter(liveData.value, referenceToFragment, viewModel)
        })
        rv.adapter = NoteAdapter(viewModel.notes.value, referenceToFragment, viewModel)

        viewModel = ViewModelProvider(requireActivity()).get(PlantRepository::class.java)

    }
}

    class NoteAdapter(val list: MutableList<Note>?, val fragment: MessageFragment, val viewModel: PlantRepository): RecyclerView.Adapter<NoteAdapter.NoteViewHolder>(){
        class NoteViewHolder(v: View): RecyclerView.ViewHolder(v){
            val text: TextView = v.findViewById(R.id.noteText)
            val time: TextView = v.findViewById(R.id.noteTimestamp)
            var plantIcon: ImageView = v.findViewById(R.id.noteIcon)

        }


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
            val singleObject= LayoutInflater.from(parent.context).inflate(R.layout.note_item_layout, parent, false)
            return NoteViewHolder(singleObject)
        }

        override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {

            if (list != null) {
                    if (list.get(position).origin.equals("Water")) {
                        holder.text.text = "Lefya ha bagnato " + getPlantNameFromCode(list.get(position).name) + " oggi!"
                    }
                    else{
                        holder.text.text = "L' acqua nel serbatoio di" + list.get(position).name + "sta finendo"
                    }
                    holder.time.text = getDateTime(list.get(position).time)
                    //aggiungere binding icone
            }
        }

        override fun getItemCount(): Int {
            if(list!=null){
                return list.size
            }
            else return 0
        }

        private fun getDateTime(s: String): String? {
                val sdf = SimpleDateFormat("MM/dd/yyyy")
                val netDate = Date(s.toLong() * 1000)
                return sdf.format(netDate)
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