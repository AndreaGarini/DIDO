package it.polito.did.provanavgraph.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import androidx.navigation.fragment.findNavController
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import it.polito.did.provanavgraph.R

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
        val db = Firebase.database.reference


        val ref1 = db.child("message1")
        val ref2 =db.child("message2")
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