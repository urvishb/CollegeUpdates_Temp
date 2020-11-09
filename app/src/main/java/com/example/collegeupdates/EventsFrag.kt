package com.example.collegeupdates

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class EventsFrag : Fragment(R.layout.fragment_events) {

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
        val view = inflater.inflate(R.layout.fragment_events, container, false)
        recyclerView = view.findViewById(R.id.rvevents)
        recyclerView.setLayoutManager(LinearLayoutManager(context))
        dataholder = ArrayList()

        val ob1 = Datamodel(R.drawable.img1, "Auditorium", "10:45 AM")
        dataholder.add(ob1)
        val ob2 = Datamodel(R.drawable.img2, "Block 1", "8:01 AM")
        dataholder.add(ob2)
        val ob3 = Datamodel(R.drawable.img3, "G20", "11:53 AM")
        dataholder.add(ob3)
        val ob4 = Datamodel(R.drawable.img4, "EC Dept", "12:43 PM")
        dataholder.add(ob4)
        val ob5 = Datamodel(R.drawable.img5, "Basketball Court", "1:05 PM")
        dataholder.add(ob5)
        val ob6 = Datamodel(R.drawable.img2, "CDC", "1:45 PM")
        dataholder.add(ob6)
        val ob7 = Datamodel(R.drawable.img1, "CSE Dept", "3:45 PM")
        dataholder.add(ob7)
        val ob8 = Datamodel(R.drawable.img3, "Auditorium", "4:40 PM")
        dataholder.add(ob8)
        val ob9 = Datamodel(R.drawable.img4, "Block 3", "5:05 PM")
        dataholder.add(ob9)
        val ob10 = Datamodel(R.drawable.img2, "MBA Block", "5:12 PM")
        dataholder.add(ob10)

        recyclerView.adapter = eventsadapter(dataholder)

        return view
    }

}
