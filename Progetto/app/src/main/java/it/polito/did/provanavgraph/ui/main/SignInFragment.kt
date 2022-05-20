package it.polito.did.provanavgraph
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController

class SignInFragment : Fragment(R.layout.fragment_sign_in) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val signIn= view.findViewById<Button>(R.id.SignIn)
        val email= view.findViewById<TextView>(R.id.emailSignIn)
        val username= view.findViewById<TextView>(R.id.usernameSignIn)
        val password= view.findViewById<TextView>(R.id.passwordSignIn)


        signIn.setOnClickListener{
            val emailText= email.text.trim()
            val userText= username.text.trim()
            val passText= password.text.trim()


        }


        signIn.setOnClickListener{
            this.requireActivity().findNavController(R.id.fragmentContainerView).navigate(R.id.action_signInFragment_to_loginFragment)
        }
    }
}