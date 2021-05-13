package com.example.collegeupdates

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_events.*
import kotlinx.android.synthetic.main.fragment_events.view.*
import kotlinx.android.synthetic.main.fragment_notice.*
import kotlinx.android.synthetic.main.fragment_notice.AddButton
import kotlinx.android.synthetic.main.fragment_notice.CameraButton
import kotlinx.android.synthetic.main.fragment_notice.WriteButton


class NoticeFrag : Fragment(R.layout.fragment_notice) {


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
        val view = inflater.inflate(R.layout.fragment_notice, container, false)
        recyclerView = view.findViewById(R.id.rvnews)
        recyclerView.setLayoutManager(LinearLayoutManager(context))
        dataholder = ArrayList()

        val ob1 = Datamodel(R.drawable.not2, "Posted @ Library", "12 Feb 2020")
        dataholder.add(ob1)
        val ob2 = Datamodel(R.drawable.not1, "Posted @ Notice Board", "17 July 2020")
        dataholder.add(ob2)
        val ob3 = Datamodel(R.drawable.not3, "Posted @ CDC", "7 June 2020")
        dataholder.add(ob3)
        val ob4 = Datamodel(R.drawable.not4, "Posted @ CSE Notice Board", "2 Aug 2020")
        dataholder.add(ob4)
        val ob5 = Datamodel(R.drawable.not5, "Posted @ Mech Notice Board", "28 Aug 2020")
        dataholder.add(ob5)
        val ob6 = Datamodel(R.drawable.not1, "Posted @ CDC Notice Board", "12 Sep 2020")
        dataholder.add(ob6)
        val ob7 = Datamodel(R.drawable.not2, "Posted @ EC Notice Board", "29 Sep 2020")
        dataholder.add(ob7)
        val ob8 = Datamodel(R.drawable.not3, "Posted @ IT Notice Board", "1 Oct 2020")
        dataholder.add(ob8)
        val ob9 = Datamodel(R.drawable.not4, "Posted @ Notice Board", "9 Oct 2020")
        dataholder.add(ob9)
        val ob10 = Datamodel(R.drawable.not5, "Posted @ Civil Notice Board", "18 Oct 2020")
        dataholder.add(ob10)

        recyclerView.adapter = newsadapter(dataholder)


        view.AddButton.setOnClickListener {
            onAddButtonClicked()
        }

//        view.WriteButton.setOnClickListener {
//            Toast.makeText(context, "Write was clicked", Toast.LENGTH_SHORT).show()
//        }

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