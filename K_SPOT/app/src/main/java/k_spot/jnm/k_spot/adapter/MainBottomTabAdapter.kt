package k_spot.jnm.k_spot.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import k_spot.jnm.k_spot.fragment.*

class MainBottomTabAdapter(private val fragmentCount : Int, fm : FragmentManager) : FragmentStatePagerAdapter(fm){
    override fun getItem(position: Int): Fragment? {
        return when (position) {
            0 -> MainPageFragment()
            1 -> CategoryPageFragment()
            2 -> MapPageFragment()
            3 -> MyPageFragment()
            else -> MyPageFragment()
        }
    }

    override fun getCount(): Int = fragmentCount
}