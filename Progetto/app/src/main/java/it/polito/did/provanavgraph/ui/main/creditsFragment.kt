package it.polito.did.provanavgraph.ui.main

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import it.polito.did.provanavgraph.R

class creditsFragment : Fragment(R.layout.fragmet_credits) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var back = view.findViewById<Button>(R.id.backCredits)

        back.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                findNavController().navigate(R.id.action_creditsFragment_to_profileFragment)

            }
        })
    }
}