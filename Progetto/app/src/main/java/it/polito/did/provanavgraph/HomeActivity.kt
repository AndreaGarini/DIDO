package it.polito.did.provanavgraph
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import it.polito.did.provanavgraph.repository.PlantRepository

class HomeActivity : AppCompatActivity() {

    lateinit var viewModel: PlantRepository


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)
        viewModel = ViewModelProvider(this).get(PlantRepository::class.java)
        viewModel.user = getIntent().getExtras()?.getString("user")!!


        val homeButton= findViewById<ImageButton>(R.id.HomeButton)
        val profileButton= findViewById<ImageButton>(R.id.ProfileButton)
        val messageButton= findViewById<ImageButton>(R.id.MessageButton)
        val container= findViewById<FragmentContainerView>(R.id.fragmentContainerView5)

        container.get


        profileButton.setOnClickListener{
            findNavController(R.id.fragmentContainerView5).navigate(R.id.action_mainFragment_to_profileFragment)
        }

        messageButton.setOnClickListener{
            findNavController(R.id.fragmentContainerView5).navigate(R.id.action_mainFragment_to_messageFragment)
        }


    }
}
