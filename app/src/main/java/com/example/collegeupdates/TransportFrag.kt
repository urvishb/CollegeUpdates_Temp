package com.example.collegeupdates

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.pdf.PdfDocument
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.browser.customtabs.CustomTabsIntent
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.model.ModelLoader
import com.example.collegeupdates.models.Buses
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_transport.*
import kotlinx.android.synthetic.main.fragment_transport.view.*
import kotlinx.android.synthetic.main.single_row_design_transport.*


private const val TAG = "TransportFrag"

class TransportFrag : Fragment(R.layout.fragment_transport) {

    private lateinit var recyclerView: RecyclerView
//    private var dataholder = ArrayList<Datamodel>()
    private lateinit var firestoreDb : FirebaseFirestore
    private lateinit var buses: MutableList<Buses>
    private lateinit var adapter: transportadapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_transport, container, false)
        recyclerView = view.findViewById(R.id.rvbuses)
        recyclerView.setLayoutManager(LinearLayoutManager(context))



        // Create the Layout File which represents one post (CardView) - Done
        // Create Data Source
        buses = mutableListOf()
        // Create the adapter
        adapter = transportadapter(context!!, buses)
        // Bind the adapter and Layout manager to the RV
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)


        // FireBase
        firestoreDb = FirebaseFirestore.getInstance()
        val busesReference = firestoreDb
            .collection("buses")
            .limit(15)
        busesReference.addSnapshotListener { snapshot, exception ->
            if (exception != null || snapshot == null)
            {
                // if we are in here, somethings wrong
                Log.e(TAG, "Exception when querying posts", exception)
                return@addSnapshotListener // returning early
            }

            val busList = snapshot.toObjects(Buses::class.java)
            buses.clear()
            buses.addAll(busList)

            adapter.notifyDataSetChanged()
            for (bus in busList) {
                Log.i(TAG, "bus $bus")
            }
        }

        view.pdfButton.setOnClickListener {
                val builder = CustomTabsIntent.Builder()
                val CustomTabsIntent = builder.build()
                CustomTabsIntent.launchUrl(context!!, Uri.parse("http://acropolis.in/wp-content/uploads/2017/08/New-Route-with-time-July-2017.pdf"))
        }




        return view
    }


}