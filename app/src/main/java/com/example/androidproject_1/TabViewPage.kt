package com.example.androidproject_1



import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class TabViewPage ( fm: FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        if (position == 0)
            fragment = TabFragment1()
        if (position == 1)
            fragment = TabFragment2()
        else if (position == 2)
            fragment = TabFragment3()

        return fragment!!
    }

    override fun getCount(): Int {
        return 3
    }

    override fun getPageTitle(position: Int): CharSequence? {
        var title: String? = null
        if (position == 0)
            title = "Battery Jumpers"
        if (position == 1)
            title = "Tow Trucks"
        else if (position == 2)
            title = "Fuel Station"
        return title
    }
}