package com.raflisalam.bfaa_github.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.raflisalam.bfaa_github.R
import com.raflisalam.bfaa_github.adapter.ListFavoriteAdapter
import com.raflisalam.bfaa_github.model.FavoriteModel
import com.raflisalam.bfaa_github.viewmodel.FavFactoryViewModel
import com.raflisalam.bfaa_github.viewmodel.FavoriteViewModel

class FavoriteActivity : AppCompatActivity() {

    private lateinit var adapter: ListFavoriteAdapter
    private lateinit var rvFavoriteUser: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        val actionBar = supportActionBar
        actionBar?.title = "Favorite Developer"
        actionBar?.setDisplayHomeAsUpEnabled(true)

        rvFavoriteUser = findViewById(R.id.rvFavorite)
        adapter = ListFavoriteAdapter()
        adapter.notifyDataSetChanged()

        val favViewModel = favViewModel(this)
        favViewModel.getAllFavorite().observe(this, {
            if (it != null) {
                adapter.setListFavorite(it)
                showListFavorite()
            }
        })
    }

    private fun showListFavorite() {
        rvFavoriteUser.layoutManager = LinearLayoutManager(this)
        rvFavoriteUser.setHasFixedSize(true)
        rvFavoriteUser.adapter = adapter
        adapter.setOnItemClickData(object : ListFavoriteAdapter.OnItemClickData {
            override fun onItemClicked(data: FavoriteModel) {
                val intent = Intent(this@FavoriteActivity, ProfileActivity::class.java)
                intent.putExtra(ProfileActivity.DETAIL_ID, data.id)
                intent.putExtra(ProfileActivity.DETAIL_USERNAME, data.login)
                intent.putExtra(ProfileActivity.DETAIL_AVATAR, data.avatar_url)
                startActivity(intent)
            }
        })
    }

    private fun favViewModel(activity: AppCompatActivity): FavoriteViewModel {
        val factoryViewModel = FavFactoryViewModel.newInstance(activity.application)
        return ViewModelProvider(activity, factoryViewModel)[FavoriteViewModel::class.java]
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}