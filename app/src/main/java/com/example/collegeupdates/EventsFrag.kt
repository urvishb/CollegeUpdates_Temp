package com.example.collegeupdates

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.format.DateUtils
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
import com.example.collegeupdates.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.fragment_events.*
import kotlinx.android.synthetic.main.fragment_events.view.*
import kotlinx.android.synthetic.main.single_row_design_events.view.*

private const val TAG = "EventsFrag"
const val FragmentValue = "FRAGMENT_VALUE"
class EventsFrag : Fragment(R.layout.fragment_events){

    private var signedInUser: User? = null
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
        // querying the database for events
        // Fetching the Events posts from fire store
        // we define the fire store database instance which points to the root at first
        // then we create a variable postsReference, and this postReference will start at the root of the DB
        // so we mention to look into the posts collection from here

        // to query all the documents in a firebase collection we use the all powerful tool known as SnapshotListener
        // >>>addSnapshotListener<<< is a way to tell firebase to inform you about any change which is made in the data
        // which is kinda crucial for us for realtime updates
        firestoreDb = FirebaseFirestore.getInstance() // this is the root
        val postsReference = firestoreDb
            .collection("posts") // specifying which collection we want
            .limit(20) // limiting the data
            .orderBy("creation_time_ms", Query.Direction.DESCENDING) // ordering it by time so the most recent post is on the top
        postsReference.addSnapshotListener { snapshot, exception ->
            //This is just an error check
            if (exception != null || snapshot == null) {
                Log.e(TAG, "Exception when querying posts", exception)
                return@addSnapshotListener
            }
            // postList will have list of all the posts from the database
            val postList = snapshot.toObjects(Post::class.java) // Encapsulating all the posts from the posts collection (which is in snapshot) to the Posts Data class

            posts.clear()
            posts.addAll(postList)
            adapter.notifyDataSetChanged()
            for (post in postList) {
                Log.i(TAG, "Post ${post}")
            }
        }

        adapter.setOnItemClickListener(object : eventsadapter.onItemClickListner{
            override fun onItemClick(position: Int) {


                val intent = Intent(context, StoryActivity::class.java)
                intent.putExtra("location", posts[position].location)
                intent.putExtra("imageUrl", posts[position].imageUrl)
                intent.putExtra("creationTime", DateUtils.getRelativeTimeSpanString(posts[position].creationTimeMs))
                startActivity(intent)
            }
        })

        // Get current signed in user
        firestoreDb.collection("users")
            .document(FirebaseAuth.getInstance().currentUser?.uid as String)
            .get().addOnSuccessListener { userSnapshot ->
                signedInUser = userSnapshot.toObject(User::class.java)
                Log.i(TAG, "signed in user: $signedInUser")
            }.addOnFailureListener { exception ->
                Log.i(TAG, "Failure fetching signed in user", exception)
            }


        view.AddButton.setOnClickListener {

            if(signedInUser?.username == "guest")
            {
                Toast.makeText(context, "Add Button is Disabled", Toast.LENGTH_SHORT).show()
            }
            else
            {
                onAddButtonClicked()
            }
        }

//        view.WriteButton.setOnClickListener {
//            Toast.makeText(context, "Write was clicked", Toast.LENGTH_SHORT).show()
//        }

        view.CameraButton.setOnClickListener {
           val intent = Intent(context, CreateCamActivity::class.java)
            intent.putExtra(FragmentValue, "events")
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

//    fun onItemClicked(post: Post) {
//
//        val toast = Toast.makeText(context, post.location, Toast.LENGTH_SHORT)
//        toast.show()
//
//    }



}
