package com.example.hiltproject.ui.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.hiltproject.data.model.User
import com.example.hiltproject.databinding.FragmentSearchBinding
import com.google.firebase.firestore.FirebaseFirestore
import java.util.UUID

class SearchFragment : Fragment() {
    lateinit var binding: FragmentSearchBinding
    lateinit var firestore: FirebaseFirestore
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       binding= FragmentSearchBinding.inflate(inflater,container,false)
        return binding.root
        firestore = FirebaseFirestore.getInstance()
         fireStore()
    }
    fun fireStore()
    {
        // Create User data
        val userInfo = User(
            name = "Kiran",
            email = "Kiran@gmail.com",
            password = "123"
        )

        // Generate unique ID (you can also use FirebaseAuth.getInstance().currentUser?.uid)
        val userId = UUID.randomUUID().toString()

        // Save to Firestore under 'user' collection
        firestore.collection("user").document(userId)
            .set(userInfo)
            .addOnSuccessListener {
                Toast.makeText(requireContext(), "User saved with ID: $userId", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                Toast.makeText(requireContext(), "Failed: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

}