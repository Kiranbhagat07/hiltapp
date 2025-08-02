package com.example.hiltproject.data.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hiltproject.data.api.ApiService
import kotlinx.coroutines.launch
import javax.inject.Inject

data class User(val name: String, val email: String, val password: String)

class menu @Inject constructor(var apiService: ApiService )
{
    suspend fun newa( name: String, email: String)=  apiService.loginUser(name,email)
}

class addMenu @Inject constructor(var menu: menu): ViewModel()
{
    fun test(name: String,email: String)
    {
        viewModelScope.launch {
            menu.newa(name,email)
        }
    }
}

