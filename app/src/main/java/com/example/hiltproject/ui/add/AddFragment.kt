package com.example.hiltproject.ui.add

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hiltproject.data.model.User
import com.example.hiltproject.databinding.FragmentAddBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging
import java.util.UUID

class AddFragment : Fragment() {

    lateinit var binding: FragmentAddBinding
    //lateinit var database: DatabaseReference
    lateinit var database: DatabaseReference
    lateinit var firestore: FirebaseFirestore


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddBinding.inflate(inflater, container, false)
       // database = FirebaseDatabase.getInstance().reference

        val sharedPref = requireContext().getSharedPreferences("AppPrefs", android.content.Context.MODE_PRIVATE)
        val currentUserId = sharedPref.getString("user_id", null)

        database= FirebaseDatabase.getInstance().reference
        firestore= FirebaseFirestore.getInstance()
      //  val userId = database.push().key ?: UUID.randomUUID().toString()
        val userId=database.push().key.toString()

        var userinfo= User("kiran","kg@gmail.com",userId)
      //  database.child("info").child(userId).setValue(userinfo)
        database.child("new").child(userId).setValue(userinfo)
        binding?.add?.setOnClickListener {
           var name = binding!!.edName.text.toString()
            var email= binding!!.edEmail.text.toString()
            var pass= binding!!.edPass.text.toString()

            var Uinfo= User(name,email,userId)
            database.child("newRecord").child(userId).setValue(Uinfo)

        }
        Log.e("INFO", userinfo.toString())




        FirebaseMessaging.getInstance().token
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val token = task.result
                    Log.d("FCM_TOKEN", "Manual token: $token")
                    binding.add.text=token
                } else {
                    Log.e("FCM_TOKEN", "Token fetch failed", task.exception)
                }
            }


        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding // prevent memory leak
    }
}
