package it.polito.did.provanavgraph.ui.main

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import it.polito.did.provanavgraph.R
import it.polito.did.provanavgraph.repository.PlantRepository
import androidx.lifecycle.Observer
import it.polito.did.provanavgraph.HomeActivity
import it.polito.did.provanavgraph.models.Plant


class Creazione_pianta : Fragment(R.layout.fragment_creazione_pianta) {
    private lateinit var viewModel: PlantRepository

    private val firstSet = arrayListOf("Fiori", "Piante aromatiche", "Altro")
    private val secondSet = arrayListOf("Aloe", "Cactus", "Zamia")
    private var buttonStatus: String= "down"

    private var newPlant: MutableMap<String, Any> = mutableMapOf()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(PlantRepository::class.java)

        viewModel.setUnicode()
        viewModel.setDesHum()
        var des = viewModel.getDes()
        var uniRef = viewModel.getUni()
        var uni: Long = 0
        var desMap : Map<String, Long> = mutableMapOf()

        val annulla = view.findViewById<Button>(R.id.annulla)
        val conferma = view.findViewById<Button>(R.id.conferma)
        val nome= view.findViewById<EditText>(R.id.nome)
        val radioGr= view.findViewById<RadioGroup>(R.id.rg)

        val dropdown= view.findViewById<TextView>(R.id.dropdown)
        var adapterList: ArrayList<String> = firstSet
        var imageId: ArrayList<Int> = arrayListOf(R.drawable.foto_flower, R.drawable.foto_aromatic, R.drawable.foto_other)
        var imageIdDown: ArrayList<Int> = arrayListOf(R.drawable.aloe, R.drawable.plantimg5, R.drawable.zamia)

        uniRef.observe(viewLifecycleOwner,Observer {
            uni = uniRef.value!!
        })

        des.observe(viewLifecycleOwner,Observer {
            desMap = des.value!!
        })

        radioGr.setOnCheckedChangeListener(object : RadioGroup.OnCheckedChangeListener{
            override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
                if (nome.text.toString() != "" && dropdown.text.toString() != ""){
                    conferma.isEnabled = true
                }
            }
        })

        nome.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (radioGr.checkedRadioButtonId != -1 && dropdown.text.toString() != ""){
                    conferma.isEnabled = true
                }
                if (s.toString().equals("")){
                    conferma.isEnabled = false
                }
            }

            override fun afterTextChanged(s: Editable?) {
              
            }

        })

        dropdown.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                val dialog: Dialog= Dialog(requireActivity())
                dialog.setContentView(R.layout.dialog_searchable_spinner)
                dialog.window?.setLayout(800, 1200 )
                dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                dialog.show()

                var searchDropdown = dialog.findViewById<SearchView>(R.id.searchDropdown)
                var image: ImageButton= dialog.findViewById(R.id.dialogImage)
                var list: ListView= dialog.findViewById(R.id.listView)
                var adapter = MyListAdapter(requireActivity(),adapterList,imageId)
                list.adapter= adapter


                image.setOnClickListener {

                    when(buttonStatus){
                        "down"->{
                            adapterList= firstSet
                            adapter.notifyDataSetChanged()
                        }
                        "back"->{
                            adapterList= secondSet
                            adapter = MyListAdapter(requireActivity(),adapterList,imageIdDown)
                            list.adapter= adapter
                            buttonStatus="down"
                            image.setImageDrawable(resources.getDrawable(R.drawable.ic_dropdown_arrow))
                        }
                    }
                }

                searchDropdown.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
                    override fun onQueryTextSubmit(query: String): Boolean {
                        createShortList(query)
                        return false
                    }

                    override fun onQueryTextChange(newText: String): Boolean {
                        createShortList(newText)
                        return false
                    }

                    fun createShortList(txt: String) {
                        var shortList: ArrayList<String> = arrayListOf()

                        when(buttonStatus){
                            "down"->{
                                if (txt != "") {
                                    for (item in firstSet) {
                                        if (item.contains(txt, ignoreCase = true)) {
                                            shortList!!.add(item)
                                        }
                                        adapterList = shortList
                                    }
                                    adapter = MyListAdapter(requireActivity(),adapterList,imageIdDown)
                                    list.adapter = adapter
                                } else {
                                    adapterList = firstSet
                                    adapter = MyListAdapter(requireActivity(),adapterList,imageIdDown)
                                    list.adapter = adapter
                                }
                            }
                            "back"->{
                                if (txt != "" && buttonStatus.equals("back")) {
                                    for (item in secondSet) {
                                        if (item.contains(txt, ignoreCase = true)) {
                                            shortList!!.add(item)
                                        }
                                        adapterList = shortList
                                    }
                                    adapter = MyListAdapter(requireActivity(),adapterList,imageIdDown)
                                    list.adapter = adapter
                                } else {
                                    Log.d("button status: ", buttonStatus)
                                    adapterList = secondSet
                                    adapter = MyListAdapter(requireActivity(),adapterList,imageIdDown)
                                    list.adapter = adapter
                                }
                            }
                        }
                    }
                })

                list.setOnItemClickListener(object : AdapterView.OnItemClickListener{
                    override fun onItemClick(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        if(buttonStatus.equals("down")){
                            newPlant.put("category", adapter.getItem(position).toString())
                            adapterList= secondSet
                            adapter = MyListAdapter(requireActivity(),adapterList,imageIdDown)
                            list.adapter= adapter
                            buttonStatus="back"
                            image.setImageDrawable(resources.getDrawable(R.drawable.ic_arrow_back))

                        }
                        else{
                            newPlant.put("species", adapter.getItem(position).toString())
                            desMap[adapter.getItem(position).toString()]?.let { newPlant.put("desHum", it) }
                            dropdown.text=adapter.getItem(position)
                            adapterList= firstSet
                            buttonStatus="down"
                            dialog.dismiss()
                            if (radioGr.checkedRadioButtonId != -1 && nome.text.toString() != ""){
                                conferma.isEnabled = true
                            }
                        }
                    }
                })
            }
        })

        annulla.setOnClickListener{
            newPlant.clear()
            val builder = AlertDialog.Builder(requireActivity())
            builder.setMessage("Sei sicuro di voler annullare? ")
                .setCancelable(false)
                .setPositiveButton("Si"){ dialog, id ->
                    newPlant.clear()
                    (activity as HomeActivity).showFooter()
                    findNavController().navigate(R.id.action_creazione_pianta_to_mainFragment)
                }
                .setNegativeButton("No") { dialog, id ->
                    dialog.dismiss()
                }
            val alert = builder.create()
            alert.show()
        }

        conferma.setOnClickListener{
            if(radioGr.checkedRadioButtonId!=-1){
                if(view.findViewById<RadioButton>(radioGr.checkedRadioButtonId).text.toString().equals("Interno"))
                {
                    newPlant.put("isOutside", "false")
                }
                else{
                    newPlant.put("isOutside", "true")
                }
            }

            newPlant.put("plantName", nome.text.trim().toString())
            newPlant.put("owner", viewModel.userDB)
            newPlant.put("humidity", 50)
            newPlant.put("waterInTank", 50)

            val plantDbKey= uni

            viewModel.db.child("plants").child(plantDbKey.toString())
                .setValue(newPlant)
            viewModel.db.child("users").child(viewModel.userDB).child("ownedPlants")
                .child( plantDbKey.toString()).setValue("true")
            findNavController().navigate(R.id.action_creazione_pianta_to_instructionsFragment)
        }

    }
}

class MyListAdapter(private val context: Activity, private val data: ArrayList<String>, private val imgid: ArrayList<Int>)
    : ArrayAdapter<String>(context, R.layout.dropdown_item, data) {

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.dropdown_item, null, true)

        val plantName: TextView = rowView.findViewById(R.id.dropdown_text)
        val image: ImageView = rowView.findViewById(R.id.dropdown_image)

        plantName.text = data[position]
        image.setImageDrawable(ContextCompat.getDrawable(context, imgid[position]))

        return rowView
    }
}
