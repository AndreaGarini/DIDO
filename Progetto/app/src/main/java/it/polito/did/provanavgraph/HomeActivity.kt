package it.polito.did.provanavgraph
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import it.polito.did.provanavgraph.repository.PlantRepository

class HomeActivity : AppCompatActivity() {

    lateinit var viewModel: PlantRepository


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)
        viewModel = ViewModelProvider(this).get(PlantRepository::class.java)


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
