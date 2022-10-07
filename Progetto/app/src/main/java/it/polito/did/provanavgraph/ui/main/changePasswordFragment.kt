package it.polito.did.provanavgraph

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import it.polito.did.provanavgraph.repository.PlantRepository

class changePasswordFragment: Fragment(R.layout.change_password_fragment) {
    private lateinit var viewModel: PlantRepository

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(PlantRepository::class.java)

        var inputUser = view.findViewById<EditText>(R.id.inputUsername)
        var inputPass = view.findViewById<EditText>(R.id.inputPassword)
        var newPass = view.findViewById<EditText>(R.id.newPassword)
        var annulla = view.findViewById<Button>(R.id.newPassAnnulla)
        var conferma = view.findViewById<Button>(R.id.newPassConferma)

        conferma.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {

            }

        })

        annulla.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {

            }

        })

    }

}