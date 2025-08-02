package com.example.hiltproject.ui.home
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hiltproject.Adapter.ChatAdapter
import com.example.hiltproject.data.model.ChatMessage
import com.example.hiltproject.databinding.FragmentHomeBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import java.util.UUID

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val db = FirebaseFirestore.getInstance()
    private val chatCollection = db.collection("chats")
    private val messages = mutableListOf<ChatMessage>()
    private lateinit var adapter: ChatAdapter
    lateinit var database: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        database= FirebaseDatabase.getInstance().reference

        adapter = ChatAdapter(messages)
        binding.recyclerViewChat.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewChat.adapter = adapter
        binding.buttonSend.setOnClickListener {
            val messageText = binding.editTextMessage.text.toString().trim()
            if (messageText.isNotEmpty()) {
                  val userId = database.push().key ?: UUID.randomUUID().toString()
                sendMessage("user_1", messageText)
                binding.editTextMessage.text?.clear()
            }
        }

        listenForMessages()
    }

    private fun sendMessage(senderId: String, message: String) {
        val chatMessage = ChatMessage(senderId, message, System.currentTimeMillis())
        chatCollection.add(chatMessage)
            .addOnFailureListener {
                Toast.makeText(requireContext(), "Failed to send message", Toast.LENGTH_SHORT).show()
            }
    }

    private fun listenForMessages() {
        chatCollection
            .orderBy("timestamp", Query.Direction.ASCENDING)
            .addSnapshotListener { snapshots, error ->
                if (error != null) {
                    Toast.makeText(requireContext(), "Error fetching messages", Toast.LENGTH_SHORT).show()
                    return@addSnapshotListener
                }

                messages.clear()
                snapshots?.toObjects(ChatMessage::class.java)?.let {
                    messages.addAll(it)
                    adapter.notifyDataSetChanged()
                    binding.recyclerViewChat.scrollToPosition(messages.size - 1)
                }
            }
    }
}

// Ensure ChatMessage is a data class like:
// data class ChatMessage(val senderId: String = "", val message: String = "", val timestamp: Long = 0L)
