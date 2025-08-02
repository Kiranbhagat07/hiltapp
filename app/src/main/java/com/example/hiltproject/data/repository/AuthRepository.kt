package com.example.hiltproject.data.repository

import com.example.hiltproject.data.api.ApiService
import com.example.hiltproject.data.model.User
import javax.inject.Inject

class AuthRepository @Inject constructor(private val api: ApiService) {
    fun registerUser(user: User) = api.registerUser(user)
    fun loginUser(email: String, password: String) = api.loginUser(email, password)
}


