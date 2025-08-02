package com.example.hiltproject.ui.register

import android.os.Bundle
import androidx.activity.viewModels
import com.example.hiltproject.base.BaseActivity
import com.example.hiltproject.data.model.User
import com.example.hiltproject.databinding.ActivityRegisterBinding
import com.example.hiltproject.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterActivity : BaseActivity<AuthViewModel>() {

    val viewModel: AuthViewModel by viewModels()
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegister.setOnClickListener {
            val name = binding.etName.text.toString().trim()
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            if (name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                viewModel.register(User(name, email, password))
                showToast("User Register")
            } else {
                viewModel.showToast("Please fill all fields")
            }
        }
    }
}
