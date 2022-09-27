package it.polito.did.provanavgraph
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import it.polito.did.provanavgraph.repository.PlantRepository
import it.polito.did.provanavgraph.ui.main.MainFragment
import android.graphics.Rect

import android.view.ViewTreeObserver
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import androidx.annotation.ContentView
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView
import it.polito.did.provanavgraph.ui.main.MessageFragment
import it.polito.did.provanavgraph.ui.main.ProfileFragment


class HomeActivity : AppCompatActivity() {

    lateinit var viewModel: PlantRepository
    lateinit var currentFrag: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)
        viewModel = ViewModelProvider(this).get(PlantRepository::class.java)
        viewModel.user = getIntent().getExtras()?.getString("user")!!


        val bottom_nav = findViewById<BottomNavigationView>(R.id.bottom_nav)
        val notesNum = findViewById<TextView>(R.id.notesNumber)
        val notesNumImage = findViewById<ImageView>(R.id.notesImageNum)

        //setCurrentTag()

        viewModel.newTimestamp()
        viewModel.setUserNotes()
        val liveNoteNum = viewModel.getUserNotes()

        liveNoteNum.observe(this, Observer {
            var counter: Int = 0
            for (note in liveNoteNum.value!!) {
                if (note.time.toDouble() > viewModel.openTimestamp) {
                    counter++
                }
            }
            Log.d("timestamp: ", viewModel.openTimestamp.toString())
            if (counter > 0) {
                notesNum.text = counter.toString()
                notesNumImage.setImageDrawable(getDrawable(R.drawable.circle))
                val color = Color.parseColor("#AE6118") //The color u want
                notesNumImage.setColorFilter(color)
            } else {
                notesNum.text = ""
                notesNumImage.setImageResource(android.R.color.transparent)
            }
        })

        val bottomListener = BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.home -> {
                    val fragment = MainFragment()
                    supportFragmentManager.beginTransaction().replace(
                        R.id.fragmentContainerView5,
                        fragment
                    )
                        .commit()
                    return@OnNavigationItemSelectedListener true
                }
                R.id.msg -> {
                    val fragment = MessageFragment()
                    supportFragmentManager.beginTransaction().replace(
                        R.id.fragmentContainerView5,
                        fragment
                    )
                        .commit()
                    return@OnNavigationItemSelectedListener true
                }
                R.id.prof -> {
                    val fragment = ProfileFragment()
                    supportFragmentManager.beginTransaction().replace(
                        R.id.fragmentContainerView5,
                        fragment
                    )
                        .commit()
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }

        bottom_nav.setOnNavigationItemSelectedListener(bottomListener)


        /* homeButton.setOnClickListener {
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
            notesNum.text = ""
            viewModel.newTimestamp()
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
    } */


        fun setCurrentTag() {
            currentFrag =
                supportFragmentManager.findFragmentById(R.id.fragmentContainerView5)?.childFragmentManager?.fragments?.get(
                    0
                )?.arguments?.get("Tag").toString()
        }

        /* public fun hideFooter(){
        homeButton.visibility = View.INVISIBLE
        profileButton.visibility = View.INVISIBLE
        messageButton.visibility = View.INVISIBLE
    }

    public fun showFooter(){
        homeButton.visibility = View.VISIBLE
        profileButton.visibility = View.VISIBLE
        messageButton.visibility = View.VISIBLE
    }*/
    }
}
