package com.example.codeiiest.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.example.codeiiest.ChatAdapter
import com.example.codeiiest.ChatModel
import com.example.codeiiest.R
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*

class MachineLearning : Fragment() {

    var list = ArrayList<ChatModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_machine_learning, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val loader: LottieAnimationView = view.findViewById(R.id.animationView)
        loader.visibility = View.VISIBLE
        val user = Firebase.auth.currentUser
        val editText: EditText = view.findViewById(R.id.editText)
        val send: ImageView = view.findViewById(R.id.sendBtn)

        val recyclerView: RecyclerView = view.findViewById(R.id.chatRecyclerView)
        val layoutManager = LinearLayoutManager(context)
        layoutManager.stackFromEnd = true
        recyclerView.layoutManager = layoutManager
        val adapter = context?.let { ChatAdapter(it, list) }
        recyclerView.adapter = adapter


        val ref = Firebase.database.reference
        ref.child("Chats").child("MachineLearning").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                list.clear()
                if(snapshot.exists()){
                    for(ds in snapshot.children){
                        val message = ds.getValue(ChatModel::class.java)
                        list.add(message!!)
                    }
                    adapter?.notifyDataSetChanged()
                    loader.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        val calendar = Calendar.getInstance()
        val sdf = SimpleDateFormat("HH:mm:ss")
        val time = sdf.format(calendar.time)

        send.setOnClickListener {
            if(editText.text.isNotEmpty()){
                val msg = ChatModel(user?.displayName.toString(), user?.photoUrl.toString(), time, editText.text.toString())
                ref.child("Chats").child("MachineLearning").push().setValue(msg)
                editText.text.clear()
                adapter?.notifyDataSetChanged()
            }
        }
    }
}