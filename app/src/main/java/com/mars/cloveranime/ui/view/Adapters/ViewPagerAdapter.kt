package com.mars.cloveranime.ui.view.Adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mars.cloveranime.ui.view.fragments.FavoriteFragment
import com.mars.cloveranime.ui.view.fragments.FinishedFragment
import com.mars.cloveranime.ui.view.fragments.MyListFragment
import com.mars.cloveranime.ui.view.fragments.PendingFragment

class ViewPagerAdapter(fragmentActivity: MyListFragment) :
    FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        when(position){
            0 -> return FavoriteFragment()
            1 -> return PendingFragment()
            2 -> return FinishedFragment()
            else -> return FavoriteFragment()
        }
    }
}