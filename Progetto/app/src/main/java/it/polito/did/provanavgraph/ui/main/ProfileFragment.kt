package it.polito.did.provanavgraph.ui.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import it.polito.did.provanavgraph.R
import it.polito.did.provanavgraph.repository.PlantRepository


class ProfileFragment : Fragment(R.layout.fragment_profile) {


    private lateinit var viewModel: PlantRepository

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(PlantRepository::class.java)

        Log.d("porco", "dio")
    }
}