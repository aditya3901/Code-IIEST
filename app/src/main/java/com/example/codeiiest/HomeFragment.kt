package com.example.codeiiest

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class HomeFragment : Fragment() {

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
        val adapter = context?.let { RecyclerAdapter(it, list) }
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

}