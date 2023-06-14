package com.capstone.mentordeck.ui.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.capstone.mentordeck.MainActivity
import com.capstone.mentordeck.R
import com.capstone.mentordeck.databinding.ActivityLoginBinding
import com.capstone.mentordeck.ui.register.RegisterActivity
import com.capstone.mentordeck.utils.ResultResponse
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private var _binding: ActivityLoginBinding? = null
    private val binding get() = _binding

    private val viewModel: LoginViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        supportActionBar?.hide()

        setLogin()
        setAnimation()

//        viewModel.isLoading.observe(this) {
//            binding?.let { loading ->
//                isLoading(true, loading.progressBar)
//            }
//        }
    }

    private fun setLogin() {
        binding?.btnLetsRegister?.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        binding?.loginButton?.setOnClickListener {
            val email = binding?.emailEditText?.text.toString()
            val password = binding?.passwordEditText?.text.toString()

            when {
                email.isEmpty() ->
                    binding?.emailEditText?.error = getString(R.string.error_email_empty)

                password.isEmpty() ->
                    binding?.passwordEditText?.error = getString(R.string.error_password_empty)

                !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() ->
                    binding?.emailEditText?.error = getString(R.string.error_invalid_email_format)

                password.length < 8 ->
                    binding?.passwordEditText?.error = getString(R.string.error_wrong_password_format)

                else -> viewModel.login(email, password).observe(this) { result ->
                    if (result != null) {
                        when (result) {
                            is ResultResponse.Loading -> {

                            }

                            is ResultResponse.Success -> {
                                AlertDialog.Builder(this).apply {
                                    setTitle(R.string.success_alert_title)
                                    setMessage(R.string.login_alert_message)
                                    setPositiveButton(R.string.continue_alert_button) { _, _ ->
                                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                        startActivity(intent)
                                        finish()
                                    }
                                    create()
                                    show()
                                }
                            }

                            is ResultResponse.Error -> {
                                when(result.error) {
                                    "Unable to resolve host \"story-api.dicoding.dev\": No address associated with hostname" -> {
                                        val builder = AlertDialog.Builder(this)
                                        val alert = builder.create()
                                        builder
                                            .setTitle(R.string.failed_response_alert_title)
                                            .setMessage(R.string.failed_response_alert_message)
                                            .setPositiveButton(R.string.back) {_, _ ->
                                                alert.cancel()
                                            }.show()
                                    }

                                    else -> {
                                        val builder = AlertDialog.Builder(this)
                                        val alert = builder.create()
                                        builder
                                            .setTitle(R.string.login_failed_alert_title)
                                            .setMessage(R.string.login_failed_alert_message)
                                            .setPositiveButton(R.string.back) {_, _ ->
                                                alert.cancel()
                                            }.show()
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun setAnimation() {
//        ObjectAnimator.ofFloat(binding?.logo, View.TRANSLATION_X, -30f, 30f).apply {
//            duration = 6000
//            repeatCount = ObjectAnimator.INFINITE
//            repeatMode = ObjectAnimator.REVERSE
//        }.start()

        val title = ObjectAnimator.ofFloat(binding?.tvTitle, View.ALPHA, 1f).setDuration(500)
        val emailEditTextLayout = ObjectAnimator.ofFloat(binding?.emailEditTextLayout, View.ALPHA, 1f).setDuration(500)
        val passwordEditTextLayout = ObjectAnimator.ofFloat(binding?.passwordEditTextLayout, View.ALPHA, 1f).setDuration(500)
        val forgot = ObjectAnimator.ofFloat(binding?.btnForgotPassword, View.ALPHA, 1f).setDuration(500)
        val login = ObjectAnimator.ofFloat(binding?.loginButton, View.ALPHA, 1f).setDuration(500)
        val register = ObjectAnimator.ofFloat(binding?.dontHaveAccountBase, View.ALPHA, 1f).setDuration(500)

        AnimatorSet().apply {
//            title,
            playSequentially(title, emailEditTextLayout, passwordEditTextLayout, forgot, login, register)
            startDelay = 500
        }.start()
    }
}