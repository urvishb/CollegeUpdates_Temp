package com.example.collegeupdates

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_notice.*


class NoticeFrag : Fragment(R.layout.fragment_notice) {

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
        val view = inflater.inflate(R.layout.fragment_notice, container, false)
        recyclerView = view.findViewById(R.id.rvnews)
        recyclerView.setLayoutManager(LinearLayoutManager(context))
        dataholder = ArrayList()

        val ob1 = Datamodel(R.drawable.img6, "Posted @ Library", "gulabi dill")
        dataholder.add(ob1)
        val ob2 = Datamodel(R.drawable.img2, "Posted @ Notice Board", "First Love")
        dataholder.add(ob2)
        val ob3 = Datamodel(R.drawable.img3, "Posted @ Auditorium", "New Crush")
        dataholder.add(ob3)
        val ob4 = Datamodel(R.drawable.img4, "Posted @ Auditorium", "Friends w Benefits")
        dataholder.add(ob4)
        val ob5 = Datamodel(R.drawable.img5, "Posted @ Auditorium", "Old Painful love")
        dataholder.add(ob5)
        val ob6 = Datamodel(R.drawable.img2, "Posted @ Auditorium", "Loyal one")
        dataholder.add(ob6)
        val ob7 = Datamodel(R.drawable.img, "Posted @ Auditorium", "Basic lobe")
        dataholder.add(ob7)
        val ob8 = Datamodel(R.drawable.img3, "Posted @ Auditorium", "Unknown people")
        dataholder.add(ob8)
        val ob9 = Datamodel(R.drawable.img4, "Posted @ Auditorium", "kya pta kon")
        dataholder.add(ob9)
        val ob10 = Datamodel(R.drawable.img, "Posted @ Auditorium", "Well Wisher")
        dataholder.add(ob10)

        recyclerView.adapter = newsadapter(dataholder)

        return view
    }


}