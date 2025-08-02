package com.example.hiltproject

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.hiltproject.databinding.ActivityMainBinding
import com.example.hiltproject.ui.login.LoginActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        val navController = navHostFragment.navController
        binding.bottomNavigation.setupWithNavController(navController)

     /*   val intent = Intent(this, LoginActivity::class.java)
        intent.putExtra("userName", "Kiran")
        intent.putExtra("userEmail", "kiran@gmail.com")
        startActivity(intent)

        var name = intent.getStringExtra("userName")
        var email=intent.getStringExtra("userEmail")*/
    }
}
