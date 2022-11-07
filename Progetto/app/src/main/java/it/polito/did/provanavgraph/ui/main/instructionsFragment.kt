package it.polito.did.provanavgraph
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import it.polito.did.provanavgraph.repository.PlantRepository

class instructionsFragment : Fragment(R.layout.instructions_fragment) {

    lateinit var viewModel: PlantRepository

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(PlantRepository::class.java)

        val text1 = view.findViewById<TextView>(R.id.textInst1)
        val text2 = view.findViewById<TextView>(R.id.textInst2)
        val text3 = view.findViewById<TextView>(R.id.textInst3)

        text1.text = "Utilizza un qualsiasi device con connessione ad internet per cercare e collegarti alla rete LEAFYA01"
        text2.text = "Una volta giunto sulla pagina di reindirizzamento seleziona il tuo wi-fi di casa, inserisci la password e premi SAVE"
        text3.text = "Attendi che la creazione della tua pianta sia completata"

        viewModel.setUnicode()
        viewModel.setWaterInTank()
        var wit: Long? = 0


        val liveData = viewModel.waterInTank
        wit = liveData.value

        liveData.observe(viewLifecycleOwner, Observer {
            wit = liveData.value
            Log.d("cond:", (wit!=null).toString())
            Log.d("cond:", (wit != 0L).toString())
            if( wit!=null && wit != 0L){
                findNavController().navigate(R.id.action_instructionsFragment_to_endCreationFragment)
            }
        })
    }
}