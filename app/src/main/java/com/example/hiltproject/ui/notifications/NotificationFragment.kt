package com.example.hiltproject.ui.notifications

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.hiltproject.data.api.ApiService
import com.example.hiltproject.data.model.*
import com.example.hiltproject.databinding.FragmentNotificationBinding
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class NotificationFragment : Fragment() {

    private lateinit var binding: FragmentNotificationBinding
    private lateinit var api: ApiService
    private val apiKey = "AIzaSyDCu7O8NRcB1r1SuC42Ch4Rhwq0mqn-Hts" // Replace with your real key

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNotificationBinding.inflate(inflater, container, false)

        // Retrofit setup
        val retrofit = Retrofit.Builder()
            .baseUrl("https://generativelanguage.googleapis.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(ApiService::class.java)

        binding.btnSend.setOnClickListener {
            val prompt = binding.editPrompt.text.toString().trim()
            if (prompt.isNotEmpty()) {
                sendPromptToGemini(prompt)
            } else {
                Toast.makeText(requireContext(), "Please enter a prompt", Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }

    private fun sendPromptToGemini(prompt: String) {
        binding.txtResponse.text = "Thinking..."

        val request = GeminiRequest(
            contents = listOf(Content(parts = listOf(Part(text = prompt))))
        )

        api.generateContent(apiKey, request).enqueue(object : Callback<GeminiResponse> {
            override fun onResponse(call: Call<GeminiResponse>, response: Response<GeminiResponse>) {
                if (response.isSuccessful) {
                    val reply = response.body()?.candidates
                        ?.firstOrNull()
                        ?.content
                        ?.parts
                        ?.firstOrNull()
                        ?.text

                    binding.txtResponse.text = reply ?: "No response from Gemini."
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("GeminiAPI", "Error ${response.code()} - $errorBody")
                    binding.txtResponse.text = "API Error ${response.code()}"
                }
            }

            override fun onFailure(call: Call<GeminiResponse>, t: Throwable) {
                Log.e("GeminiAPI", "Failed: ${t.message}")
                binding.txtResponse.text = "Error: ${t.message}"
            }
        })
    }
}
