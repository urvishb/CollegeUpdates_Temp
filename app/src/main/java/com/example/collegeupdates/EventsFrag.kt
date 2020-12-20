package com.example.collegeupdates

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.annotation.RequiresPermission
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_events.*
import kotlinx.android.synthetic.main.fragment_events.view.*

class EventsFrag : Fragment(R.layout.fragment_events) {

    private val rotateOpen: Animation by lazy { AnimationUtils.loadAnimation(context, R.anim.rotate_open_anim) }
    private val rotateClose: Animation by lazy { AnimationUtils.loadAnimation(context, R.anim.rotate_close_anim) }
    private val fromBottom: Animation by lazy { AnimationUtils.loadAnimation(context, R.anim.from_bottom_anim) }
    private val toBottom: Animation by lazy { AnimationUtils.loadAnimation(context, R.anim.to_bottom_anim) }

    private var clicked = false

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

        val ob1 = Datamodel(R.drawable.img5, "Auditorium", "10:45 AM")
        dataholder.add(ob1)
        val ob2 = Datamodel(R.drawable.img2, "Block I", "8:01 AM")
        dataholder.add(ob2)
        val ob3 = Datamodel(R.drawable.img3, "G20", "11:53 AM")
        dataholder.add(ob3)
        val ob4 = Datamodel(R.drawable.img3, "EC Dept", "12:43 PM")
        dataholder.add(ob4)
        val ob5 = Datamodel(R.drawable.img1, "Basketball Court", "1:05 PM")
        dataholder.add(ob5)
        val ob6 = Datamodel(R.drawable.img6, "CDC", "1:45 PM")
        dataholder.add(ob6)
        val ob7 = Datamodel(R.drawable.img7, "CSE Dept", "3:45 PM")
        dataholder.add(ob7)
        val ob8 = Datamodel(R.drawable.img4, "Auditorium", "4:40 PM")
        dataholder.add(ob8)
        val ob9 = Datamodel(R.drawable.img7, "Block III", "5:05 PM")
        dataholder.add(ob9)
        val ob10 = Datamodel(R.drawable.img2, "MBA Block", "5:12 PM")
        dataholder.add(ob10)


        recyclerView.adapter = eventsadapter(dataholder)

        view.AddButton.setOnClickListener {
            onAddButtonClicked()
        }

        view.WriteButton.setOnClickListener {
            Toast.makeText(context, "Write was clicked", Toast.LENGTH_SHORT).show()
        }

        view.CameraButton.setOnClickListener {
            Toast.makeText(context, "Camera was Clicked", Toast.LENGTH_SHORT).show()
        }


        return view
    }


    private fun onAddButtonClicked() {
        setVisibility(clicked)
        setAnimation(clicked)
        clicked = !clicked
        setClickable(clicked)

    }

    private fun setAnimation(clicked: Boolean) {
        if (!clicked) {
            WriteButton.startAnimation(fromBottom)
            CameraButton.startAnimation(fromBottom)
            AddButton.startAnimation(rotateOpen)
        }
        else {
            WriteButton.startAnimation(toBottom)
            CameraButton.startAnimation(toBottom)
            AddButton.startAnimation(rotateClose)
        }
    }

    private fun setVisibility(clicked: Boolean) {
        if(!clicked) {
            WriteButton.visibility = View.VISIBLE
            CameraButton.visibility = View.VISIBLE
        }
        else {
            WriteButton.visibility = View.INVISIBLE
            CameraButton.visibility = View.INVISIBLE
        }
    }

    private fun setClickable(clicked: Boolean) {
        if(!clicked) {
            WriteButton.isClickable = false
            CameraButton.isClickable = false
        }
        else {
            WriteButton.isClickable = true
            CameraButton.isClickable = true
        }

    }

}
