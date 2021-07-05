package com.example.codeiiest

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter(val context: Context, val list: ArrayList<PostData>): RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val viewHolder = RecyclerViewHolder(LayoutInflater.from(context).inflate(R.layout.recycler_item, parent, false))

//        viewHolder.url.setOnClickListener {
//            listener.onItemClick(list[viewHolder.adapterPosition])
//        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        val currentPost = list[position]
        holder.titleView.text = currentPost.title
        holder.dt.text = currentPost.dateTime
        holder.cont.text = currentPost.context
        holder.url.text = currentPost.link
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class RecyclerViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val titleView: TextView = itemView.findViewById(R.id.titleView)
        val dt: TextView = itemView.findViewById(R.id.dateTimeView)
        val cont: TextView = itemView.findViewById(R.id.contextView)
        val url: TextView = itemView.findViewById(R.id.linkView)
    }
}

//interface onClickHandler {
//    fun onItemClick(post: PostData)
//}