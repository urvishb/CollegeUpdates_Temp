package com.example.collegeupdates

import android.content.Context
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
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.collegeupdates.databinding.FragmentEventsBinding
import com.example.collegeupdates.models.Post
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.fragment_events.*
import kotlinx.android.synthetic.main.fragment_events.view.*
import kotlinx.android.synthetic.main.single_row_design_events.view.*

private const val TAG = "EventsFrag"
class EventsFrag : Fragment(R.layout.fragment_events){

    private lateinit var firestoreDb: FirebaseFirestore
    private lateinit var posts: MutableList<Post>
    private lateinit var adapter: eventsadapter




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

        // Create the Layout File which represents one post (CardView) - Done
        // Create Data Source - Done
        posts = mutableListOf()
        // Create the adapter
        adapter = eventsadapter(context!!, posts)
        // Bind the adapter and Layout manager to the RV
        recyclerView.adapter = adapter
        recyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)


        // FireBase
        firestoreDb = FirebaseFirestore.getInstance() // this is the root
        val postsReference = firestoreDb
            .collection("posts")
            .limit(20)
            .orderBy("creation_time_ms", Query.Direction.DESCENDING)
        postsReference.addSnapshotListener { snapshot, exception ->
            //This is just an error check
            if (exception != null || snapshot == null) {
                Log.e(TAG, "Exception when querying posts", exception)
                return@addSnapshotListener
            }
            val postList = snapshot.toObjects(Post::class.java)
            posts.clear()
            posts.addAll(postList)
            adapter.notifyDataSetChanged()
            for (post in postList) {
                Log.i(TAG, "Post ${post}")
            }
        }


        view.AddButton.setOnClickListener {
            onAddButtonClicked()
        }

//        view.WriteButton.setOnClickListener {
//            Toast.makeText(context, "Write was clicked", Toast.LENGTH_SHORT).show()
//        }

        view.CameraButton.setOnClickListener {
           val intent = Intent(context, CreateCamActivity::class.java)
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
        CameraButton.isClickable = clicked

    }

    fun onItemClicked(post: Post) {

        val toast = Toast.makeText(context, post.location, Toast.LENGTH_SHORT)
        toast.show()

    }



}
