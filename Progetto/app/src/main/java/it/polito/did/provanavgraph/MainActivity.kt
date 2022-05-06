package it.polito.did.provanavgraph
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Layout
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import it.polito.did.provanavgraph.repository.PlantRepository
import kotlin.math.log

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: PlantRepository


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        viewModel = ViewModelProvider(this).get(PlantRepository::class.java)

        val button= findViewById<Button>(R.id.button)
        val username= findViewById<TextView>(R.id.username)
        val password= findViewById<TextView>(R.id.password)

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
                        Log.d("pass: ", "true")
                        val i = Intent(this, HomeActivity::class.java)
                        startActivity(i)
                    }
                    else
                    {
                        //toast per dire utente non trovato o password sbagliata
                    }
                }
            }

            else{
                //toast per dire che la mail è sbagliata
                }
        }



    }
}







