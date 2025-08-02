package com.example.hiltproject.data.api

import com.example.hiltproject.data.model.GeminiRequest
import com.example.hiltproject.data.model.GeminiResponse
import com.example.hiltproject.data.model.LoginResponse
import com.example.hiltproject.data.model.RegisterResponse
import com.example.hiltproject.data.model.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @POST("register-api/register.php")
    fun registerUser(@Body user: User): Call<RegisterResponse>

    @GET("register-api/login.php")
    fun loginUser(@Query("email") email: String, @Query("password") password: String): Call<LoginResponse>

    @POST("v1/models/gemini-1.5-pro:generateContent")
    fun generateContent(
        @Query("key") apiKey: String,
        @Body request: GeminiRequest
    ): Call<GeminiResponse>
}
