package com.raflisalam.bfaa_github.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.raflisalam.bfaa_github.api.ApiClient
import com.raflisalam.bfaa_github.model.DataUser
import com.raflisalam.bfaa_github.model.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchUserViewModel: ViewModel() {

    val usersList = MutableLiveData<ArrayList<DataUser>>()

    fun setUsers(query: String) { ApiClient.instance.searchUsers(query).enqueue(object : Callback<UserResponse> {
                override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                    if (response.isSuccessful) {
                        usersList.postValue(response.body()?.dataItems)
                    }
                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    t.message?.let { Log.d("Fail Load!", it) }
                }
            })
    }

    fun getUsers(): LiveData<ArrayList<DataUser>> {
        return usersList
    }
}