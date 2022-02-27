package com.raflisalam.bfaa_github.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.raflisalam.bfaa_github.api.ApiClient
import com.raflisalam.bfaa_github.model.DataUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailProfileViewModel: ViewModel() {

    val usersList = MutableLiveData<DataUser>()
    val listFollowers = MutableLiveData<ArrayList<DataUser>>()
    val listFollowing = MutableLiveData<ArrayList<DataUser>>()

    fun setUsers(username: String) {
        ApiClient.instance.detailUser(username).enqueue(object : Callback<DataUser> {
                override fun onResponse(call: Call<DataUser>, response: Response<DataUser>) {
                    if (response.isSuccessful) {
                        usersList.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<DataUser>, t: Throwable) {
                    t.message?.let { Log.d("Fail Load!", it) }
                }
            })
    }

    fun setFollowers(username: String) {
        ApiClient.instance.getFollowers(username).enqueue(object : Callback<ArrayList<DataUser>> {
            override fun onResponse(call: Call<ArrayList<DataUser>>, response: Response<ArrayList<DataUser>>) {
                if (response.isSuccessful) {
                    listFollowers.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<ArrayList<DataUser>>, t: Throwable) {
                t.message?.let { Log.d("Fail Load!", it) }
            }
        })
    }

    fun setFollowing(username: String) {
        ApiClient.instance.getFollowing(username).enqueue(object : Callback<ArrayList<DataUser>> {
            override fun onResponse(call: Call<ArrayList<DataUser>>, response: Response<ArrayList<DataUser>>
            ) {
                if (response.isSuccessful) {
                    listFollowing.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<ArrayList<DataUser>>, t: Throwable) {
                t.message?.let { Log.d("Fail Load!", it) }
            }
        })
    }

    fun getDetailProfile(): LiveData<DataUser> {
        return usersList
    }

    fun getFollowers(): LiveData<ArrayList<DataUser>> {
        return listFollowers
    }

    fun getFollowing(): LiveData<ArrayList<DataUser>> {
        return listFollowing
    }
}