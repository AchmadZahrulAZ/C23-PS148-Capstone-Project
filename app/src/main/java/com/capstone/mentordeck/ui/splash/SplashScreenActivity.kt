package com.capstone.mentordeck.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.capstone.mentordeck.MainActivity
import com.capstone.mentordeck.ui.home.HomeViewModel
import com.capstone.mentordeck.ui.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint


@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashScreenActivity : AppCompatActivity() {

    private val viewModel: HomeViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.getUser().observe(this) { user ->
            if (user.isLogin) {
                val intent = Intent(this, MainActivity::class.java)
                splashScreen(intent)
            } else {
                val intent = Intent(this, LoginActivity::class.java)
                splashScreen(intent)
            }
        }

//        startActivity(Intent(this@SplashScreenActivity, MainActivity::class.java))
//        finish()

    }
    private fun splashScreen(intent: Intent) {
        startActivity(intent)
        finish()
    }
}