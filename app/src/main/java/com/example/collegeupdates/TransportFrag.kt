package com.example.collegeupdates

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class TransportFrag : Fragment(R.layout.fragment_transport) {

    private lateinit var recyclerView: RecyclerView
    private var dataholder = ArrayList<Datamodel>()


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
        dataholder = ArrayList()

        val ob1 = Datamodel(R.drawable.buslogo, "G42", "Stop 1 > Stop 2 > Stop 3 > Stop 4 > Stop 5 > College")
        dataholder.add(ob1)
        val ob2 = Datamodel(R.drawable.buslogo, "G50", "Stop 1 > Stop 2 > Stop 3 > Stop 4 > Stop 5 > College")
        dataholder.add(ob2)
        val ob3 = Datamodel(R.drawable.buslogo, "G41", "Stop 1 > Stop 2 > Stop 3 > Stop 4 > Stop 5 > College")
        dataholder.add(ob3)
        val ob4 = Datamodel(R.drawable.buslogo, "G35", "Stop 1 > Stop 2 > Stop 3 > Stop 4 > Stop 5 > College")
        dataholder.add(ob4)
        val ob5 = Datamodel(R.drawable.buslogo, "G12", "Stop 1 > Stop 2 > Stop 3 > Stop 4 > Stop 5 > College")
        dataholder.add(ob5)
        val ob6 = Datamodel(R.drawable.buslogo, "G32", "Stop 1 > Stop 2 > Stop 3 > Stop 4 > Stop 5 > College")
        dataholder.add(ob6)
        val ob7 = Datamodel(R.drawable.buslogo, "G51", "Stop 1 > Stop 2 > Stop 3 > Stop 4 > Stop 5 > College")
        dataholder.add(ob7)
        val ob8 = Datamodel(R.drawable.buslogo, "G11", "Stop 1 > Stop 2 > Stop 3 > Stop 4 > Stop 5 > College")
        dataholder.add(ob8)
        val ob9 = Datamodel(R.drawable.buslogo, "G08", "Stop 1 > Stop 2 > Stop 3 > Stop 4 > Stop 5 > College")
        dataholder.add(ob9)
        val ob10 = Datamodel(R.drawable.buslogo, "G20", "Stop 1 > Stop 2 > Stop 3 > Stop 4 > Stop 5 > College")
        dataholder.add(ob10)

        recyclerView.adapter = transportadapter(dataholder)

        return view
    }

}