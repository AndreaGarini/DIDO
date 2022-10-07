package it.polito.did.provanavgraph
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.findNavController
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import it.polito.did.provanavgraph.repository.PlantRepository
import java.util.*
import java.util.Timer




class endCreationFragment : Fragment(R.layout.fragment_endcreation) {

    lateinit var viewModel: PlantRepository

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(PlantRepository::class.java)

        val timer: CountDownTimer = object :  CountDownTimer(3000, 1000) {
            override fun onTick(millisUntilFinished: Long) {

            }

            override fun onFinish() {
                (activity as HomeActivity).showFooter()
                findNavController().navigate(R.id.action_endCreationFragment_to_mainFragment)
            }
        }

        timer.start()

    }
}
