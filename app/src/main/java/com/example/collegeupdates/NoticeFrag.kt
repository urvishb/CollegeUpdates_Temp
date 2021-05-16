package com.example.collegeupdates

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.collegeupdates.models.Notice
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.fragment_events.*
import kotlinx.android.synthetic.main.fragment_events.view.*
import kotlinx.android.synthetic.main.fragment_notice.*
import kotlinx.android.synthetic.main.fragment_notice.AddButton
import kotlinx.android.synthetic.main.fragment_notice.CameraButton

private const val TAG = "NoticeFrag"
class NoticeFrag : Fragment(R.layout.fragment_notice) {

    private lateinit var firestoreDb : FirebaseFirestore
    private lateinit var notices: MutableList<Notice>
    private lateinit var adapter: newsadapter


    private val rotateOpen: Animation by lazy { AnimationUtils.loadAnimation(context, R.anim.rotate_open_anim) }
    private val rotateClose: Animation by lazy { AnimationUtils.loadAnimation(context, R.anim.rotate_close_anim) }
    private val fromBottom: Animation by lazy { AnimationUtils.loadAnimation(context, R.anim.from_bottom_anim) }
    private val toBottom: Animation by lazy { AnimationUtils.loadAnimation(context, R.anim.to_bottom_anim) }

    private var clicked = false

    private lateinit var recyclerView: RecyclerView


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



        // Create the Layout File which represents one post (CardView) - Done
        // Create Data Source
        notices = mutableListOf()
        // Create the adapter
        adapter = newsadapter(context!!, notices)
        // Bind the adapter and Layout manager to the RV
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)


        //Firebase
        firestoreDb = FirebaseFirestore.getInstance()
        val noticeReference = firestoreDb
            .collection("notices")
            .limit(15)
            .orderBy("creation_time_ms", Query.Direction.DESCENDING)
        noticeReference.addSnapshotListener { snapshot, exception ->
            if (exception != null || snapshot == null)
            {
                // if we are in here, somethings wrong
                Log.e(TAG, "Exception when querying posts", exception)
                return@addSnapshotListener // returning early
            }

            val noticeList = snapshot.toObjects(Notice::class.java)
            notices.clear()
            notices.addAll(noticeList)

            adapter.notifyDataSetChanged()
            for (notice in noticeList) {
                Log.i(TAG, "notice $notice")
            }
        }


        view.AddButton.setOnClickListener {
            onAddButtonClicked()
        }

//        view.WriteButton.setOnClickListener {
//            Toast.makeText(context, "Write was clicked", Toast.LENGTH_SHORT).show()
//        }

        view.CameraButton.setOnClickListener {
//            Toast.makeText(context, "Camera was Clicked", Toast.LENGTH_SHORT).show()
            val intent = Intent(context, CreateCamActivity::class.java)
            intent.putExtra(FragmentValue, "notices")
            startActivity(intent)
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
            //WriteButton.startAnimation(fromBottom)
            CameraButton.startAnimation(fromBottom)
            AddButton.startAnimation(rotateOpen)
        }
        else {
            //WriteButton.startAnimation(toBottom)
            CameraButton.startAnimation(toBottom)
            AddButton.startAnimation(rotateClose)
        }
    }

    private fun setVisibility(clicked: Boolean) {
        if(!clicked) {
            //WriteButton.visibility = View.VISIBLE
            CameraButton.visibility = View.VISIBLE
        }
        else {
            //WriteButton.visibility = View.INVISIBLE
            CameraButton.visibility = View.INVISIBLE
        }
    }

    private fun setClickable(clicked: Boolean) {
        if(!clicked) {
            //WriteButton.isClickable = false
            CameraButton.isClickable = false
        }
        else {
            //WriteButton.isClickable = true
            CameraButton.isClickable = true
        }

    }


}