package com.raflisalam.bfaa_github.ui

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import android.widget.ToggleButton
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.raflisalam.bfaa_github.R
import com.raflisalam.bfaa_github.adapter.FragmentAdapter
import com.raflisalam.bfaa_github.model.FavoriteModel
import com.raflisalam.bfaa_github.viewmodel.DetailProfileViewModel
import com.raflisalam.bfaa_github.viewmodel.FavFactoryViewModel
import com.raflisalam.bfaa_github.viewmodel.FavoriteViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfileActivity : AppCompatActivity() {

    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager2
    private lateinit var fragmentAdapter: FragmentAdapter
    private lateinit var viewModel: DetailProfileViewModel
    private var isFavorite: Boolean = false

    companion object{
        @StringRes
        private val TAB_TITTLE = intArrayOf(
            R.string.title_followers,
            R.string.title_following
        )

        const val DETAIL_ID = "id"
        const val DETAIL_USERNAME = "username"
        const val DETAIL_AVATAR = "img"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val id = intent.getIntExtra(DETAIL_ID, 0)
        val username = intent.getStringExtra(DETAIL_USERNAME).toString()
        val avatar = intent.getStringExtra(DETAIL_AVATAR)
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.title = username
        tabLayout = findViewById(R.id.tabLayout)
        viewPager = findViewById(R.id.viewPager)

        val detailNama = findViewById<TextView>(R.id.detailNama)
        val detailLocation = findViewById<TextView>(R.id.detailLocation)
        val detailRepo = findViewById<TextView>(R.id.detailRepo)
        val detailCompany = findViewById<TextView>(R.id.detailCompany)
        val detailFollowers = findViewById<TextView>(R.id.detailFollowers)
        val detailFollowing = findViewById<TextView>(R.id.detailFollowing)
        val detailImg = findViewById<ImageView>(R.id.detailImg)
        val vectorLocation = findViewById<ImageView>(R.id.vectorLocation)
        val vectorCompany = findViewById<ImageView>(R.id.vectorCompany)
        val btnFavorite = findViewById<ToggleButton>(R.id.btnFavorite)

        fragmentAdapter = FragmentAdapter(this, username)
        viewPager.adapter = fragmentAdapter
        TabLayoutMediator(tabLayout,viewPager) { tab, position ->
            tab.view.isClickable = true
            tab.text = resources.getString(TAB_TITTLE[position])
        }.attach()

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(DetailProfileViewModel::class.java)
        viewModel.setUsers(username)
        viewModel.getDetailProfile().observe(this, {
            if (it !=null) {
                Glide.with(this@ProfileActivity)
                    .load(it.imgUser)
                    .into(detailImg)
                detailNama.text = it.name
                detailLocation.text = it.location
                detailCompany.text = it.company
                detailRepo.text = "${it.repo}"
                detailFollowers.text = "${it.followers}"
                detailFollowing.text = "${it.following}"
                if (detailCompany == null) {
                    vectorCompany.visibility = View.INVISIBLE
                } else if (detailLocation == null) {
                    vectorLocation.visibility = View.INVISIBLE
                } else {
                    vectorCompany.visibility = View.VISIBLE
                    vectorLocation.visibility = View.VISIBLE
                }
            }
        })

        val favViewModel = favViewModel(this)
        CoroutineScope(Dispatchers.IO).launch {
            val count = favViewModel.getUser(username)
            withContext(Dispatchers.Main) {
                if (count != null) {
                    if (count > 0.toString()) {
                        btnFavorite.isChecked = true
                        isFavorite = true
                    } else {
                        btnFavorite.isChecked = false
                        isFavorite = false
                    }
                }
            }
        }

        btnFavorite.setOnClickListener {
            isFavorite = !isFavorite
            if (isFavorite) {
                favViewModel.addFavorite(id, username, avatar)
                Toast.makeText(this, "Added To Favorite Developer!", Toast.LENGTH_SHORT).show()
            } else {
                favViewModel.deleteUser(id)
                Toast.makeText(this, "Delete From Favorite Developer!", Toast.LENGTH_SHORT).show()
            }
            btnFavorite.isChecked = isFavorite
        }
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