package com.example.codeiiest

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class NewPostFragment : Fragment() {

    private lateinit var database: DatabaseReference

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_new__post, container, false)

        val doneBtn = root.findViewById<FloatingActionButton>(R.id.done)
        val titleET = root.findViewById<EditText>(R.id.ed_1)
        val contextET = root.findViewById<EditText>(R.id.ed_2)
        val linkET = root.findViewById<EditText>(R.id.ed_3)

        doneBtn.setOnClickListener {
            if(titleET.text.isNotEmpty() && contextET.text.isNotEmpty() && linkET.text.isNotEmpty()){

                val currentDateTime = LocalDateTime.now()
                val post = PostData(titleET.text.toString(), contextET.text.toString(),
                    linkET.text.toString(), currentDateTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")))

                database = Firebase.database.reference
                database.child("Post").child(currentDateTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"))).setValue(post)

                Toast.makeText(context, "Post Successful", Toast.LENGTH_SHORT).show()
                val transaction = activity?.supportFragmentManager?.beginTransaction()
                transaction?.replace(R.id.fragment_container, HomeFragment())
                transaction?.disallowAddToBackStack()
                transaction?.commit()
            }

            else {
                Toast.makeText(context, "Post Cancelled", Toast.LENGTH_SHORT).show()
                val transaction = activity?.supportFragmentManager?.beginTransaction()
                transaction?.replace(R.id.fragment_container, HomeFragment())
                transaction?.disallowAddToBackStack()
                transaction?.commit()
            }
        }

        return root
    }
}