package it.polito.did.provanavgraph
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import it.polito.did.provanavgraph.repository.PlantRepository

class SignInFragment : Fragment(R.layout.fragment_sign_in) {

    lateinit var viewModel: PlantRepository

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(PlantRepository::class.java)


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
            var newUser: MutableMap<String,String?> = mutableMapOf()

            if(emailText.contains("@") && emailText.contains(".") )
            {

                    if (passText.length>6 && passText.any{it in numbers} /*&& passText.contains("?=.*[A-Z]")*/) {
                        newUser.put("email", emailText.toString())
                        newUser.put("password", passText.toString())
                        newUser.put("ownedPlants", "null")
                        newUser.put("username", userText.toString())

                        viewModel.ref2.child("user"+(viewModel.usersCount+1)).setValue(newUser)
                        val i = Intent(this.activity, HomeActivity::class.java)
                        val b= Bundle()
                        b.putString("user", emailText.toString())
                        i.putExtras(b)
                        startActivity(i)
                    }
                    else{
                        Toast.makeText(this.activity, "La password deve contenere almeno un numero ed una maiuscola", Toast.LENGTH_LONG).show()
                    }
                }

                else {
                Toast.makeText(this.activity, "Email errata ", Toast.LENGTH_LONG).show()
            }
        }
    }
}