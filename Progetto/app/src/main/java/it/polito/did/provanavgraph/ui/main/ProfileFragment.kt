package it.polito.did.provanavgraph.ui.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import it.polito.did.provanavgraph.R
import it.polito.did.provanavgraph.repository.PlantRepository


class ProfileFragment : Fragment(R.layout.fragment_profile) {


    private lateinit var viewModel: PlantRepository

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(PlantRepository::class.java)

        var passButton = view.findViewById<Button>(R.id.changePassButton)
        var creditsButton = view.findViewById<Button>(R.id.creditsButton)

        passButton.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View?) {
                findNavController().navigate(R.id.action_profileFragment_to_changePasswordFragment)
            }
        })

        creditsButton.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View?) {
                findNavController().navigate(R.id.action_profileFragment_to_creditsFragment)
            }
        })
    }
}