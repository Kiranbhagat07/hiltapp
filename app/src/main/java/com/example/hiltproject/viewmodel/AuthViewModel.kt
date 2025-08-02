package com.example.hiltproject.viewmodel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.hiltproject.data.model.LoginResponse
import com.example.hiltproject.data.model.RegisterResponse
import com.example.hiltproject.data.model.User
import com.example.hiltproject.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: AuthRepository
) : BaseViewModel() {

    private val _registerResult = MutableLiveData<String>()
    val registerResult: LiveData<String> get() = _registerResult

    private val _loginResult = MutableLiveData<String>()
    val loginResult: LiveData<String> get() = _loginResult

    fun register(user: User) {
        repository.registerUser(user).enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                if (response.isSuccessful) {
                    _registerResult.postValue(response.body()?.message ?: "Register success")
                } else {
                    _registerResult.postValue("Register failed")
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                _registerResult.postValue(t.message ?: "Register error")
            }
        })
    }

    fun login(email: String, password: String) {
        repository.loginUser(email, password).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    _loginResult.postValue(response.body()?.message ?: "Login success")
                } else {
                    _loginResult.postValue("Login failed")
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                _loginResult.postValue(t.message ?: "Login error")
            }
        })
    }
}