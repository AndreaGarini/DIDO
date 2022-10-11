package it.polito.did.provanavgraph

import android.app.AlertDialog
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import it.polito.did.provanavgraph.repository.PlantRepository

class changePasswordFragment: Fragment(R.layout.change_password_fragment) {
    private lateinit var viewModel: PlantRepository

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(PlantRepository::class.java)

        var inputPass = view.findViewById<EditText>(R.id.inputPassword)
        var newPass = view.findViewById<EditText>(R.id.newPassword)
        var annulla = view.findViewById<Button>(R.id.newPassAnnulla)
        var conferma = view.findViewById<Button>(R.id.newPassConferma)
        var back = view.findViewById<Button>(R.id.backPass)

        viewModel.setUserName()

        val liveKey : LiveData<String> = viewModel.getUserKey()
        var dbKey : String? = liveKey.value

        liveKey.observe(viewLifecycleOwner, Observer {
            dbKey = liveKey.value.toString()
        })

        val timer: CountDownTimer = object :  CountDownTimer(2000, 1000) {
            override fun onTick(millisUntilFinished: Long) {

            }

            override fun onFinish() {
                (activity as HomeActivity).showFooter()
                findNavController().navigate(R.id.action_changePasswordFragment_to_profileFragment)
            }
        }


        conferma.setOnClickListener(object : View.OnClickListener{
            // TODO: 07/10/22 se il cambio password non va controlla qui
            override fun onClick(v: View?) {
                if (inputPass.text.toString().trim() == viewModel.userPass.value.toString().trim()){
                    viewModel.db.child("users").child(dbKey!!).child("password").setValue(newPass.text.toString())
                    Toast.makeText(requireActivity(), "Password modificata!", Toast.LENGTH_LONG).show()
                    timer.start()
                }
                else{
                    Log.d("password: ", viewModel.userPass.value.toString().trim())
                    Log.d("myPassword: ", inputPass.text.toString().trim())
                    Toast.makeText(requireActivity(), "Password o username errati!", Toast.LENGTH_LONG).show()
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