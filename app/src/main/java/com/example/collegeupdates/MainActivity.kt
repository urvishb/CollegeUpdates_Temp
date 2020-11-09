package com.example.collegeupdates

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_notice.*

class MainActivity : AppCompatActivity() {
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
                R.id.miEvents -> setCurrentFragment(firstFragment)
                R.id.miNotice -> setCurrentFragment(secondFragment)
                R.id.miBuses -> setCurrentFragment(thirdFragment)
            }
            true
        }

        bottomNavigationView.getOrCreateBadge(R.id.miEvents).apply {
            number = 7
            isVisible = true
        }

        bottomNavigationView.getOrCreateBadge(R.id.miNotice).apply {
            number = 2
            isVisible = true
        }

    }

    private fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, fragment)
            commit()
        }
}
