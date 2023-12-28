package com.example.suitmediaapk.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.suitmediaapk.R
import com.example.suitmediaapk.dataclass.data

class UsersAdapter(private val context: Context,
                   private var userList: List<data>,
                   private val itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<UsersAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.account_suitmedia_row, parent, false)
        return ViewHolder(view)
    }

    interface OnItemClickListener {
        fun onItemClick(data: data)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = userList[position]
        holder.txtFirstName.text = user.first_name
        holder.txtLastName.text = user.last_name
        holder.txtEmail.text = user.email

        Glide.with(context)
            .load(user.avatar)
            .error(R.drawable.screen)
            .into(holder.avatarImage)

        holder.itemView.setOnClickListener {
            itemClickListener.onItemClick(user)
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    fun setData(users: List<data>) {
        userList = users
        notifyDataSetChanged()
    }

    fun getData(): List<data> {
        return userList
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtFirstName: TextView = itemView.findViewById(R.id.firstname)
        val txtLastName: TextView = itemView.findViewById(R.id.lastname)
        val txtEmail: TextView = itemView.findViewById(R.id.email)
        val avatarImage: ImageView = itemView.findViewById(R.id.profilepicture)
    }
}