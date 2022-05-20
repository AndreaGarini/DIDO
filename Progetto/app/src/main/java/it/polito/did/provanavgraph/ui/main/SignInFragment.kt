package it.polito.did.provanavgraph
import android.content.Intent
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
            val numbers = "0123456789"
            val symbols= "/?!:;%"

            if(emailText.contains("@") && userText.contains(".") )
            {
                    if (passText.length<8 && passText.any{it in symbols}  ) {
                        val i = Intent(this.activity, HomeActivity::class.java)
                        val b= Bundle()
                        i.putExtras(b)
                        startActivity(i)
                    }
                    else{
                        Toast.makeText(this.activity, "La password deve contenere almeno un numero", Toast.LENGTH_LONG).show()
                    }
                }

                else {
                Toast.makeText(this.activity, "Email errata ", Toast.LENGTH_LONG).show()
            }
        }


        signIn.setOnClickListener{
            this.requireActivity().findNavController(R.id.fragmentContainerView).navigate(R.id.action_signInFragment_to_loginFragment)
        }
    }
}