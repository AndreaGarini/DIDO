package it.polito.did.provanavgraph
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController

class SignInFragment : Fragment(R.layout.fragment_sign_in) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val signIn= view.findViewById<Button>(R.id.singIn2)

        signIn.setOnClickListener{
            this.requireActivity().findNavController(R.id.fragmentContainerView).navigate(R.id.action_signInFragment_to_loginFragment)
        }
    }
}