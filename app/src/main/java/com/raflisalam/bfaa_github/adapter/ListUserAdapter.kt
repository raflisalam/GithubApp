package com.raflisalam.bfaa_github.adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.raflisalam.bfaa_github.R
import com.raflisalam.bfaa_github.model.DataUser

class ListUserAdapter : RecyclerView.Adapter<ListUserAdapter.ViewHolder>(){

    private val listUsers = ArrayList<DataUser>()
    private lateinit var onItemClickData: OnItemClickData

    fun setListUsers(users: ArrayList<DataUser>) {
        listUsers.clear()
        listUsers.addAll(users)
        notifyDataSetChanged()
    }

    fun setOnItemClickData (onItemClickData: OnItemClickData) {
        this.onItemClickData = onItemClickData
    }

    interface OnItemClickData {
        fun onItemClicked(data: DataUser)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var namaUser = itemView.findViewById<TextView>(R.id.tvNamaUser)
        var imageUser = itemView.findViewById<ImageView>(R.id.imgUser)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.list_user, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listUsers[position]
        Glide.with(holder.itemView.context)
            .load(item.imgUser)
            .apply(RequestOptions())
            .into(holder.imageUser)
        holder.namaUser.text = item.username
        holder.itemView.setOnClickListener {
            onItemClickData.onItemClicked(item)
        }
    }

    override fun getItemCount(): Int {
        return listUsers.size
    }
}