package it.polito.did.provanavgraph

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
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
        var back = view.findViewById<ImageButton>(R.id.backPass)

        conferma.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                if (inputUser.text.trim().equals(viewModel.userName.value) && inputPass.text.trim().equals(viewModel.userPass.value)){
                    viewModel.db.child("users").child(viewModel.user)
                        .setValue(newPass.text)
                    Toast.makeText(requireActivity(), "Password modificata!", Toast.LENGTH_LONG).show()
                }
            }
        })

        annulla.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                val builder = AlertDialog.Builder(requireActivity())
                builder.setMessage("Sei sicuro di voler annullare? ")
                    .setCancelable(false)
                    .setPositiveButton("Si"){ dialog, id ->
                        findNavController().navigate(R.id.action_changePasswordFragment_to_profileFragment)
                    }
                    .setNegativeButton("No") { dialog, id ->
                        dialog.dismiss()
                    }
                val alert = builder.create()
                alert.show()

            }
        })

        back.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View?) {
                findNavController().navigate(R.id.action_changePasswordFragment_to_profileFragment)
            }

        })

    }

}