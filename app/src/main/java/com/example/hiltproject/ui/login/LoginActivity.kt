package com.example.hiltproject.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.hiltproject.MainActivity
import com.example.hiltproject.base.BaseActivity
import com.example.hiltproject.databinding.ActivityLoginBinding
import com.example.hiltproject.ui.register.RegisterActivity
import com.example.hiltproject.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : BaseActivity<AuthViewModel>() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupObservers()
        setupListeners()
        val sharedPref = getSharedPreferences("AppPrefs", MODE_PRIVATE)
        val isLoggedIn = sharedPref.getBoolean("is_logged_in", false)
        if (isLoggedIn) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
            return
        }
    }

    private fun setupListeners() {
        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()


            if (email.isEmpty()) {
                Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            viewModel.login(email, password)
        }

        binding.goToRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun setupObservers() {
        viewModel.loginResult.observe(this) { message ->
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            // Optionally navigate if login is successful
            if (message.contains("success", ignoreCase = true)) {
              /*  val sharedPref = getSharedPreferences("AppPrefs", MODE_PRIVATE)
                sharedPref.edit().putBoolean("is_logged_in", true).apply()
                 startActivity(Intent(this, MainActivity::class.java))
                 finish()*/
                redirectIfLoggedIn(MainActivity::class.java) // <-- from BaseActivity
            }
        }
    }
}
