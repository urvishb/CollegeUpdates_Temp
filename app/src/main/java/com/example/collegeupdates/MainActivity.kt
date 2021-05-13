package com.example.collegeupdates

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.collegeupdates.models.Post
import com.example.collegeupdates.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_notice.*
import java.util.Timer

private const val TAG = "MainActivity"
const val EXTRA_USERNAME = "EXTRA_USERNAME"

class MainActivity : AppCompatActivity(){

    private var signedInUser: User? = null
    private lateinit var firestoreDb: FirebaseFirestore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val firstFragment = EventsFrag()
        val secondFragment = NoticeFrag()
        val thirdFragment = TransportFrag()

        bottomNavigationView.setSelectedItemId(R.id.miEvents)

        setCurrentFragment(firstFragment)

        bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.miEvents ->
                {
                    setCurrentFragment(firstFragment)
                }
                R.id.miNotice -> {
                    setCurrentFragment(secondFragment)
                }

                R.id.miBuses -> {
                    setCurrentFragment(thirdFragment)
                }

            }
            true
        }

        bottomNavigationView.getOrCreateBadge(R.id.miEvents).apply {
            isVisible = true

            // TODO if the feed is refreshed then bring the badge back
//            val selectedItemId = bottomNavigationView.selectedItemId
//            if(selectedItemId == R.id.miEvents)
//            {
//                isVisible = false;
//            }
            backgroundColor = ContextCompat.getColor(this@MainActivity, R.color.blue)

        }

        bottomNavigationView.getOrCreateBadge(R.id.miNotice).apply {
            isVisible = true
            val selectedItemId = bottomNavigationView.selectedItemId
            // TODO if the feed is refreshed then bring the badge back
//            if(selectedItemId == R.id.miNotice)
//            {
//                isVisible = false;
//            }
            backgroundColor = ContextCompat.getColor(this@MainActivity, R.color.blue)

        }


        // FireBase query for username
        firestoreDb = FirebaseFirestore.getInstance() // this is the root

        firestoreDb.collection("users")
            .document(FirebaseAuth.getInstance().currentUser?.uid as String)
            .get()
            .addOnSuccessListener { userSnapshot ->
                signedInUser = userSnapshot.toObject(User::class.java)
                Log.i(TAG, "signed in user: $signedInUser")
            }
            .addOnFailureListener{ exception ->
                Log.i(TAG, "Failure fetching signed in user", exception)
            }


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_posts, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menu_profile)
        {
            val intent = Intent(this, ProfileActivity::class.java)
            intent.putExtra(EXTRA_USERNAME, signedInUser?.username)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, fragment)
            commit()
        }


}
