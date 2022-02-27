package com.raflisalam.bfaa_github.database

import androidx.recyclerview.widget.DiffUtil
import com.raflisalam.bfaa_github.model.FavoriteModel

class FavoriteDiffCallback(private val oldFavoriteList: List<FavoriteModel>, private val newFavoriteList: List<FavoriteModel>): DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldFavoriteList.size
    }

    override fun getNewListSize(): Int {
        return newFavoriteList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldFavoriteList[oldItemPosition].id == newFavoriteList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val newEmployee = newFavoriteList[newItemPosition]
        val oldEmployee = oldFavoriteList[oldItemPosition]
        return newEmployee.login == oldEmployee.login
    }

}