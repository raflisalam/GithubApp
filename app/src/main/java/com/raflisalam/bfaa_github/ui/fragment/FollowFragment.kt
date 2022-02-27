package com.raflisalam.bfaa_github.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.raflisalam.bfaa_github.R
import com.raflisalam.bfaa_github.adapter.ListUserAdapter
import com.raflisalam.bfaa_github.model.DataUser
import com.raflisalam.bfaa_github.ui.ProfileActivity
import com.raflisalam.bfaa_github.viewmodel.DetailProfileViewModel

class FollowFragment : Fragment() {

    private lateinit var viewModel: DetailProfileViewModel
    private lateinit var adapter: ListUserAdapter
    private lateinit var rvFollow: RecyclerView

    companion object {

        const val ARG_SECTION_NUMBER = "section_number"
        private const val DETAIL_USERNAME = "username"
        fun newInstance(index: Int, username: String) = FollowFragment().apply {
            arguments = Bundle().apply {
                putInt(ARG_SECTION_NUMBER, index)
                putString(DETAIL_USERNAME, username)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sectionIndex = arguments?.getInt(ARG_SECTION_NUMBER, 0)
        val username = arguments?.getString(DETAIL_USERNAME)
        Log.d("gagal", username + username)

        rvFollow = view.findViewById(R.id.rvFollow)

        adapter = ListUserAdapter()
        adapter.notifyDataSetChanged()

        if (sectionIndex==0) {
            viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(DetailProfileViewModel::class.java)
            if (username != null) {
                viewModel.setFollowers(username)
            }
            viewModel.getFollowers().observe(viewLifecycleOwner, {
                if (it != null) {
                    adapter.setListUsers(it)
                }
                showListFollow()
            })
        } else {
            viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(DetailProfileViewModel::class.java)
            if (username != null) {
                viewModel.setFollowing(username)
            }
            viewModel.getFollowing().observe(viewLifecycleOwner, {
                if (it != null) {
                    adapter.setListUsers(it)
                }
                showListFollow()
            })
        }
    }

    private fun showListFollow() {
        rvFollow.setHasFixedSize(true)
        rvFollow.layoutManager = LinearLayoutManager(activity)
        rvFollow.adapter = adapter
        adapter.setOnItemClickData(object : ListUserAdapter.OnItemClickData {
            override fun onItemClicked(data: DataUser) {
                val intent = Intent(activity, ProfileActivity::class.java)
                intent.putExtra(ProfileActivity.DETAIL_USERNAME, data.username)
                startActivity(intent)
            }
        })
    }
}