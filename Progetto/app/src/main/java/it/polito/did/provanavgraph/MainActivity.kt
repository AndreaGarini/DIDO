package it.polito.did.provanavgraph
import android.content.Intent
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
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import it.polito.did.provanavgraph.repository.PlantRepository

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: PlantRepository


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val button= findViewById<Button>(R.id.button)

        button.setOnClickListener{
               val i= Intent(this, HomeActivity::class.java)
               startActivity(i)
        }
    }
}







