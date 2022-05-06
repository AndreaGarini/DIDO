package it.polito.did.provanavgraph
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import it.polito.did.provanavgraph.repository.PlantRepository

class LoginFragment : Fragment(R.layout.fragment_login) {

    lateinit var viewModel: PlantRepository


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(PlantRepository::class.java)

        val button= view.findViewById<Button>(R.id.signIn2)
        val signIn= view.findViewById<Button>(R.id.signIn)
        val username= view.findViewById<TextView>(R.id.username)
        val password= view.findViewById<TextView>(R.id.password)

        var userList: MutableMap<String,String> = mutableMapOf()

        viewModel.ref2.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (item in snapshot.children){
                    userList.put(item.child("email").value.toString(), item.child("password").value.toString())

                }
                Log.d("userList", userList.toString())
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })

        button.setOnClickListener{
            val user= username.text.toString().trim()
            val pass= password.text.toString()
            Log.d("user: ", user)

            if(user.contains("@") && user.contains("."))
            {
                Log.d("contains: ", "true")
                if (userList.keys.contains(user))
                {
                    Log.d("contains user: ", "true")
                    if (userList.get(user) == pass)
                    {
                        val i= Intent(this.activity, HomeActivity::class.java)
                        startActivity(i)
                    }
                    else
                    {
                       Toast.makeText(this.activity, "Mail o password errati", Toast.LENGTH_LONG).show()
                    }
                }
            }

            else{
                Toast.makeText(this.activity, "Mail errata", Toast.LENGTH_LONG).show()
            }
        }

        signIn.setOnClickListener{
            this.requireActivity().findNavController(R.id.fragmentContainerView).navigate(R.id.action_loginFragment_to_signInFragment)
        }



    }
}
