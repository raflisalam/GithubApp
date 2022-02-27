package com.raflisalam.bfaa_github.ui

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.switchmaterial.SwitchMaterial
import com.raflisalam.bfaa_github.R
import com.raflisalam.bfaa_github.adapter.ListUserAdapter
import com.raflisalam.bfaa_github.model.DataUser
import com.raflisalam.bfaa_github.pref.SettingsPreference
import com.raflisalam.bfaa_github.viewmodel.FactoryViewModel
import com.raflisalam.bfaa_github.viewmodel.SearchUserViewModel
import com.raflisalam.bfaa_github.viewmodel.ThemeModeViewModel
import com.tuann.floatingactionbuttonexpandable.FloatingActionButtonExpandable


class HomeActivity : AppCompatActivity() {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    private lateinit var adapter: ListUserAdapter
    private lateinit var viewModel: SearchUserViewModel
    private lateinit var themeViewModel: ThemeModeViewModel
    private lateinit var rvListUser: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val floatButton = findViewById<FloatingActionButtonExpandable>(R.id.floatButton)
        floatButton.setOnClickListener {
            val intent = Intent(this, FavoriteActivity::class.java)
            startActivity(intent)
        }

        rvListUser = findViewById(R.id.rvUser)
        adapter = ListUserAdapter()
        adapter.notifyDataSetChanged()

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(SearchUserViewModel::class.java)
        viewModel.getUsers().observe(this, {
            if (it!=null) {
                adapter.setListUsers(it)
                showListRecyler()
            }
        })

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)

        val switchButton = menu?.findItem(R.id.switch_button)?.actionView as SwitchMaterial
        val pref = SettingsPreference.newInstance(dataStore)
        themeViewModel = ViewModelProvider(this, FactoryViewModel(pref)).get(ThemeModeViewModel::class.java)
        themeViewModel.getThemeMode().observe(this, { isNightModeActive: Boolean ->
            if (isNightModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                switchButton.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                switchButton.isChecked = false
            }
        })

        switchButton.setOnCheckedChangeListener { _, isChecked ->
            themeViewModel.setThemeMode(isChecked)
        }

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu?.findItem(R.id.searching)?.actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val q = newText.toString()
                if (q.isEmpty()) return true
                viewModel.setUsers(q)
                return true
            }

        })
        return true
    }

    private fun showListRecyler() {
        rvListUser.layoutManager = LinearLayoutManager(this@HomeActivity)
        rvListUser.setHasFixedSize(true)
        rvListUser.adapter = adapter
        adapter.setOnItemClickData(object: ListUserAdapter.OnItemClickData {
            override fun onItemClicked(data: DataUser) {
                val intent = Intent(this@HomeActivity, ProfileActivity::class.java)
                intent.putExtra(ProfileActivity.DETAIL_ID, data.id)
                intent.putExtra(ProfileActivity.DETAIL_USERNAME, data.username)
                intent.putExtra(ProfileActivity.DETAIL_AVATAR, data.imgUser)
                startActivity(intent)
            }
        })
    }
}