package it.polito.did.provanavgraph

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)


        val homeButton= findViewById<ImageButton>(R.id.HomeButton)
        val profileButton= findViewById<ImageButton>(R.id.ProfileButton)
        val messageButton= findViewById<ImageButton>(R.id.MessageButton)

        /*if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView5, MainFragment.newInstance())
                .commitNow()
        }*/

        profileButton.setOnClickListener{
            findNavController(R.id.fragmentContainerView5).navigate(R.id.action_mainFragment_to_profileFragment)
        }

        messageButton.setOnClickListener{
            findNavController(R.id.fragmentContainerView5).navigate(R.id.action_mainFragment_to_messageFragment)
        }


    }
}

data class Plant (var name: String, var species : String)







