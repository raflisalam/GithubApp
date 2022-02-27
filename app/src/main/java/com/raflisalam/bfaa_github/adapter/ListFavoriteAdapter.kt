package com.raflisalam.bfaa_github.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.raflisalam.bfaa_github.R
import com.raflisalam.bfaa_github.database.FavoriteDiffCallback
import com.raflisalam.bfaa_github.model.FavoriteModel

class ListFavoriteAdapter: RecyclerView.Adapter<ListFavoriteAdapter.ViewHolder>() {
    private val listFavoriteUsers = ArrayList<FavoriteModel>()
    private lateinit var onItemClickData: ListFavoriteAdapter.OnItemClickData

    fun setListFavorite(favUsers: List<FavoriteModel>) {
        val favDiffCallback = FavoriteDiffCallback(this.listFavoriteUsers, listFavoriteUsers)
        val favDiffResult = DiffUtil.calculateDiff(favDiffCallback)
        this.listFavoriteUsers.clear()
        this.listFavoriteUsers.addAll(favUsers)
        notifyDataSetChanged()
        favDiffResult.dispatchUpdatesTo(this)
    }

    fun setOnItemClickData(onItemClickData: OnItemClickData) {
        this.onItemClickData = onItemClickData
    }

    interface OnItemClickData {
        fun onItemClicked(data: FavoriteModel)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var namaUser = itemView.findViewById<TextView>(R.id.tvNamaUser)
        var imageUser = itemView.findViewById<ImageView>(R.id.imgUser)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.list_user, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listFavoriteUsers[position]
        Glide.with(holder.itemView.context)
            .load(item.avatar_url)
            .apply(RequestOptions())
            .into(holder.imageUser)
        holder.namaUser.text = item.login
        holder.itemView.setOnClickListener {
            onItemClickData.onItemClicked(item)
        }
    }

    override fun getItemCount(): Int {
        return listFavoriteUsers.size
    }
}