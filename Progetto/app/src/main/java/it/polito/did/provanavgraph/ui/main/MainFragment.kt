package it.polito.did.provanavgraph.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
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
        val nome = view.findViewById<Button>(R.id.ToSinglePlant)
        val db = Firebase.database.reference

        val ref = db.child("message")
        ref.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                nome.text = snapshot.getValue<String>()

            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)

    }



}