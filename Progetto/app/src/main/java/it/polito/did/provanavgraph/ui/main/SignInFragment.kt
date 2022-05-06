package it.polito.did.provanavgraph
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Layout
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
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
import it.polito.did.provanavgraph.ui.main.MainViewModel
import kotlin.math.log

class SignInFragment : Fragment(R.layout.fragment_sign_in) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val signIn= view.findViewById<Button>(R.id.signIn2)

        signIn.setOnClickListener{
            this.requireActivity().findNavController(R.id.fragmentContainerView).navigate(R.id.action_signInFragment_to_loginFragment)
        }
    }
}