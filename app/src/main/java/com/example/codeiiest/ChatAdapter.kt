package com.example.codeiiest

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ChatAdapter(private val context: Context, private val list: ArrayList<ChatModel>) : RecyclerView.Adapter<ChatAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.msg_bubble, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = list[position]
        holder.name.text = currentItem.userName
        holder.dateTime.text = currentItem.dateTime
        holder.msg.text = currentItem.message

        Glide.with(context).load(currentItem.userImage).placeholder(R.drawable.ic_baseline_account_circle_24).into(holder.image)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.userName)
        val image: ImageView = itemView.findViewById(R.id.userImage)
        val dateTime: TextView = itemView.findViewById(R.id.dateTime)
        val msg: TextView = itemView.findViewById(R.id.msg_text)
    }


}