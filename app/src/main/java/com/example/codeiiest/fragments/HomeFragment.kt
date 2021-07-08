package com.example.codeiiest.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.codeiiest.*
import com.example.codeiiest.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*

class HomeFragment : Fragment() , OnClickHandler {

    var list = ArrayList<PostData>()
    lateinit var ref: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        val loader = root.findViewById<ProgressBar>(R.id.loader)
        loader.visibility = View.VISIBLE

        val fabBtn = root.findViewById<FloatingActionButton>(R.id.fab)
        fabBtn.setOnClickListener {
            Toast.makeText(context, "Create New Post", Toast.LENGTH_SHORT).show()
            val transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(R.id.fragment_container, NewPostFragment())
            transaction?.disallowAddToBackStack()
            transaction?.commit()
        }

        val recyclerView = root.findViewById<RecyclerView>(R.id.recyclerView)
        val layoutManager = LinearLayoutManager(context)
        layoutManager.stackFromEnd = true
        recyclerView.layoutManager = layoutManager
        val adapter = context?.let { RecyclerAdapter(it, list, this) }
        recyclerView.adapter = adapter

        ref = FirebaseDatabase.getInstance().getReference("Post")
        ref.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()) {
                    for(ds in snapshot.children){
                        val post = ds.getValue(PostData::class.java)
                        list.add(post!!)
                    }
                    adapter?.notifyDataSetChanged()
                    loader.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

        return root
    }

    override fun onItemClick(post: PostData) {
        val url: String = post.link
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        startActivity(intent)
    }

}