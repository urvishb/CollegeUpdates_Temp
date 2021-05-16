package com.example.collegeupdates

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.collegeupdates.models.Notice
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.activity_user_notice.*
import kotlinx.android.synthetic.main.fragment_notice.*

private const val TAG = "UserNoticeActivity"
class UserNoticeActivity : AppCompatActivity() {

    private lateinit var firestoreDb : FirebaseFirestore
    private lateinit var notices: MutableList<Notice>
    private lateinit var adapter: NoticeProfileAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_notice)


        // Create the Layout File which represents one post (CardView) - Done
        // Create Data Source
        notices = mutableListOf()
        // Create the adapter
        adapter = NoticeProfileAdapter(this, notices)
        rvUserNotice.adapter = adapter
        rvUserNotice.layoutManager = LinearLayoutManager(this)
        rvUserNotice.layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)



        //Firebase
        firestoreDb = FirebaseFirestore.getInstance()
        var noticeReference = firestoreDb
            .collection("notices")
            .limit(15)
            .orderBy("creation_time_ms", Query.Direction.DESCENDING)

        val username = intent.getStringExtra(EXTRA_USERNAME)
        if (username != null)
        {
            supportActionBar?.title = username
            noticeReference = noticeReference.whereEqualTo("user.username", username)
        }
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
                Log.i(TAG, "UserNotice $notice")
            }
        }
    }
}