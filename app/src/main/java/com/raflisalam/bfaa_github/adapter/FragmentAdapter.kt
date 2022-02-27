package com.raflisalam.bfaa_github.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.raflisalam.bfaa_github.ui.fragment.FollowFragment

class FragmentAdapter(activity: AppCompatActivity, private val username: String) : FragmentStateAdapter(activity) {

    override fun createFragment(position: Int): Fragment {
        return FollowFragment.newInstance(position, username)
    }

    override fun getItemCount(): Int {
        return 2
    }
}