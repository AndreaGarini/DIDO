package it.polito.did.provanavgraph
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import it.polito.did.provanavgraph.repository.PlantRepository

class HomeActivity : AppCompatActivity() {

    lateinit var viewModel: PlantRepository
    lateinit var currentFrag: String

    lateinit var homeButton: ImageButton
    lateinit var profileButton: ImageButton
    lateinit var messageButton: ImageButton
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)
        viewModel = ViewModelProvider(this).get(PlantRepository::class.java)
        viewModel.user = getIntent().getExtras()?.getString("user")!!


        homeButton= findViewById<ImageButton>(R.id.HomeButton)
        profileButton= findViewById<ImageButton>(R.id.ProfileButton)
        messageButton= findViewById<ImageButton>(R.id.MessageButton)

        setCurrentTag()

        homeButton.setOnClickListener {
            setCurrentTag()
            if (currentFrag.equals("profile")) {
                findNavController(R.id.fragmentContainerView5).navigate(R.id.action_profileFragment_to_mainFragment)
            }
            if (currentFrag.equals("message")) {
                findNavController(R.id.fragmentContainerView5).navigate(R.id.action_messageFragment_to_mainFragment)
            }
            if (currentFrag.equals("single")) {
                findNavController(R.id.fragmentContainerView5).navigate(R.id.action_singlePlantFragment_to_mainFragment)
            }
        }
        profileButton.setOnClickListener{
            setCurrentTag()
             if (currentFrag.equals("main")) {
                 findNavController(R.id.fragmentContainerView5).navigate(R.id.action_mainFragment_to_profileFragment)
             }
            if (currentFrag.equals("message")) {
                findNavController(R.id.fragmentContainerView5).navigate(R.id.action_messageFragment_to_profileFragment)
            }
            if (currentFrag.equals("single")) {
                findNavController(R.id.fragmentContainerView5).navigate(R.id.action_singlePlantFragment_to_profileFragment)
            }
        }

        messageButton.setOnClickListener{
            setCurrentTag()
            if (currentFrag.equals("main")) {
                findNavController(R.id.fragmentContainerView5).navigate(R.id.action_mainFragment_to_messageFragment)
            }
            if (currentFrag.equals("profile")) {
                findNavController(R.id.fragmentContainerView5).navigate(R.id.action_profileFragment_to_messageFragment)
            }
            if (currentFrag.equals("single")) {
                findNavController(R.id.fragmentContainerView5).navigate(R.id.action_singlePlantFragment_to_messageFragment)
            }
        }


    }


    fun setCurrentTag(){
        currentFrag= supportFragmentManager.findFragmentById(R.id.fragmentContainerView5)?.childFragmentManager?.fragments?.
        get(0)?.arguments?.get("Tag").toString()
    }

    fun hideFooter(){
        homeButton.visibility = View.INVISIBLE
        profileButton.visibility = View.INVISIBLE
        messageButton.visibility = View.INVISIBLE
    }

    fun showFooter(){
        homeButton.visibility = View.VISIBLE
        profileButton.visibility = View.VISIBLE
        messageButton.visibility = View.VISIBLE
    }
}
