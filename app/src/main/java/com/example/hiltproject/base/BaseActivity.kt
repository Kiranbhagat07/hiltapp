package com.example.hiltproject.base
import com.example.hiltproject.viewmodel.BaseViewModel
import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresPermission
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import com.example.hiltproject.MainActivity
import com.example.hiltproject.utils.NetworkUtils

abstract class BaseActivity<VM : BaseViewModel> : AppCompatActivity() {

    private lateinit var rootLayout: FrameLayout
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBaseLayout()
    }

    // Inflate base layout with loading
    private fun setupBaseLayout() {
        rootLayout = FrameLayout(this).apply {
            layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
            )
        }

        progressBar = ProgressBar(this).apply {
            visibility = View.GONE
            isIndeterminate = true
            val params = FrameLayout.LayoutParams(
                150, 150
            )
            params.topMargin = 300
            layoutParams = params
        }

        rootLayout.addView(progressBar)
        super.setContentView(rootLayout)
    }

    // Let child activity set its layout inside root
    override fun setContentView(layoutResID: Int) {
        val view = LayoutInflater.from(this).inflate(layoutResID, rootLayout, false)
        rootLayout.addView(view)
    }

    // ViewModel generic access
    protected inline fun <reified T : ViewModel> getViewModel(): T {
        return viewModels<T>().value
    }

    // Loading indicator control
    fun showLoading() {
        progressBar.visibility = View.VISIBLE
    }

    fun hideLoading() {
        progressBar.visibility = View.GONE
    }

    // Toast utility
    fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    // Error handler
    fun showError(error: String) {
        showToast("Error: $error")
    }

    // Network utility
    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    fun checkNetworkConnection(): Boolean {
        return NetworkUtils.isNetworkConnected(this)
    }

    // Permission request utility
    fun requestPermissionsIfNeeded(permissions: Array<String>, requestCode: Int) {
        val needed = permissions.filter {
            ContextCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED
        }

        if (needed.isNotEmpty()) {
            ActivityCompat.requestPermissions(this, needed.toTypedArray(), requestCode)
        } else {
            showToast("All permissions granted")
        }
    }

    // Keyboard utility
    fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        currentFocus?.let {
            imm.hideSoftInputFromWindow(it.windowToken, 0)
        }
    }

    // Intent utility
    fun navigateToActivity(destinationClass: Class<out Activity>) {
        startActivity(Intent(this, destinationClass))
    }

    // SharedPreferences helpers
    fun saveToPreferences(key: String, value: String) {
        val sharedPreferences = getSharedPreferences("AppPrefs", MODE_PRIVATE)
        sharedPreferences.edit().putString(key, value).apply()
    }

    fun getFromPreferences(key: String): String? {
        return getSharedPreferences("AppPrefs", MODE_PRIVATE).getString(key, null)
    }
    fun redirectIfLoggedIn(destinationClass: Class<*>) {
        val sharedPref = getSharedPreferences("AppPrefs", MODE_PRIVATE)
        val isLoggedIn = sharedPref.getBoolean("is_logged_in", false)
        if (isLoggedIn) {
            startActivity(Intent(this, destinationClass))
            finish()
        }
        sharedPref.edit().putBoolean("is_logged_in", true).apply()
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }


    // Toolbar utility
    fun setupToolbar(title: String) {
        supportActionBar?.title = title
    }
}
