package com.example.androidproject_1

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout

class TabHost : AppCompatActivity() {

    lateinit var fragmentManager : FragmentManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tab_host)


        var viewPager = findViewById<ViewPager>(R.id.tabviewpager)
        var tabLayout = findViewById<TabLayout>(R.id.tabs)

        var viewPagerAdapter = TabViewPage(supportFragmentManager)
        viewPager.adapter = viewPagerAdapter

        // It is used to join TabLayout with ViewPager.
        tabLayout.setupWithViewPager(viewPager)


        fragmentManager = supportFragmentManager
    }
}