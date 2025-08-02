package com.example.hiltproject.Adapter

import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hiltproject.R
import com.example.hiltproject.data.model.ChatMessage

class ChatAdapter(private val messages: List<ChatMessage>) : RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {

    private val currentUserId = "user_1" // Replace with actual logged-in user ID

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_chat_message, parent, false)
        return ChatViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val message = messages[position]
        holder.bind(message, message.senderId == currentUserId)
    }

    override fun getItemCount(): Int = messages.size

    inner class ChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textMessage: TextView = itemView.findViewById(R.id.textMessage)

        fun bind(message: ChatMessage, isSender: Boolean) {
            textMessage.text = message.message

            val params = textMessage.layoutParams as ViewGroup.MarginLayoutParams
            if (isSender) {
                textMessage.setBackgroundResource(R.drawable.bg_message_self)
                params.marginEnd = 50
                params.marginStart = 100
            } else {
                textMessage.setBackgroundResource(R.drawable.bg_message_self)
                params.marginStart = 50
                params.marginEnd = 100
            }
            textMessage.layoutParams = params

        }
    }
}
