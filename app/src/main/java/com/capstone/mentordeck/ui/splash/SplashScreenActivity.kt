package com.capstone.mentordeck.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.capstone.mentordeck.MainActivity
import com.capstone.mentordeck.databinding.ActivitySplashBinding
import com.capstone.mentordeck.ui.home.HomeViewModel
import com.capstone.mentordeck.ui.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint


@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashScreenActivity : AppCompatActivity() {

    private var _binding: ActivitySplashBinding? = null
    private val binding get() = _binding

    private val viewModel: HomeViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        supportActionBar?.hide()

        viewModel.getUser().observe(this) { user ->
            if (user.isLogin) {
                val intent = Intent(this, MainActivity::class.java)
                splashScreen(intent)
            } else {
                val intent = Intent(this, LoginActivity::class.java)
                splashScreen(intent)
            }
        }
    }

    private fun splashScreen(intent: Intent) {
        Handler(Looper.getMainLooper()).postDelayed ({
            startActivity(intent)
            finish()
        }, SPLASH_DELAY.toLong())
    }

    companion object {
        const val SPLASH_DELAY = 3000
    }
}