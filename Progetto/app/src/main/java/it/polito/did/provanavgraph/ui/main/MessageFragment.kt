package it.polito.did.provanavgraph.ui.main

import android.os.Build
import android.os.Bundle
import android.text.format.DateFormat.format
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import it.polito.did.provanavgraph.R
import it.polito.did.provanavgraph.models.Note
import it.polito.did.provanavgraph.repository.PlantRepository
import java.lang.String.format
import java.text.DateFormat
import java.text.MessageFormat.format
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
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
            val text: TextView = v.findViewById(R.id.infoText)
            val time: TextView = v.findViewById(R.id.infoTimestamp)
            var plantIcon: ImageView = v.findViewById(R.id.infoIcon)

        }


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
            val singleObject= LayoutInflater.from(parent.context).inflate(R.layout.note_item_layout, parent, false)
            return NoteViewHolder(singleObject)
        }

        @RequiresApi(Build.VERSION_CODES.O)
        override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {

            if (list != null) {
                    if (list.get(position).origin.equals("Water")) {
                        holder.text.text = "Leafya ha bagnato " + getPlantNameFromCode(list.get(position).name) + " oggi!"
                        holder.plantIcon.setImageDrawable(fragment.activity?.let {
                            ContextCompat.getDrawable(
                                it, R.drawable.ic_baseline_bubble_chart_24)
                        })
                    }
                    else{
                        holder.text.text = "L' acqua di " + getPlantNameFromCode(list.get(position).name) + " sta finendo"
                        holder.plantIcon.setImageDrawable(fragment.activity?.let {
                            ContextCompat.getDrawable(
                                it, R.drawable.ic_baseline_water_damage_24)
                        })
                    }
                    holder.time.text = convertDate(list.get(position).time)

            }
        }

        override fun getItemCount(): Int {
            if(list!=null){
                return list.size
            }
            else return 0
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