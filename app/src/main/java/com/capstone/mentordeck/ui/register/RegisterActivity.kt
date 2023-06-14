package com.capstone.mentordeck.ui.register

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.capstone.mentordeck.R
import com.capstone.mentordeck.databinding.ActivityRegisterBinding
import com.capstone.mentordeck.ui.login.LoginActivity
import com.capstone.mentordeck.utils.ResultResponse
import com.capstone.mentordeck.utils.isLoading
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {

    private var _binding: ActivityRegisterBinding? = null
    private val binding get() = _binding

    private val viewModel: RegisterViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        supportActionBar?.hide()

        setRegister()
        setAnimation()

        viewModel.isLoading.observe(this) {
            binding?.let { loading ->
                isLoading(true, loading.progressBar)
            }
        }
    }

    private fun setRegister() {
        binding?.btnLetsLogin?.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        binding?.registerButton?.setOnClickListener {
            val name = binding?.nameEditText?.text.toString()
            val email = binding?.emailEditText?.text.toString()
            val password = binding?.passwordEditText?.text.toString()

            when {
                name.isEmpty() ->
                    binding?.nameEditText?.error = getString(R.string.error_name_empty)

                email.isEmpty() ->
                    binding?.emailEditText?.error = getString(R.string.error_email_empty)

                password.isEmpty() ->
                    binding?.passwordEditText?.error = getString(R.string.error_password_empty)

                !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() ->
                    binding?.emailEditText?.error = getString(R.string.error_invalid_email_format)

                password.length < 8 ->
                    binding?.passwordEditText?.error = getString(R.string.error_wrong_password_format)

                else -> viewModel.register(name, email, password).observe(this) { result ->
                    if (result != null) {
                        when (result) {
                            is ResultResponse.Loading -> {

                            }

                            is ResultResponse.Success -> {
                                AlertDialog.Builder(this).apply {
                                    setTitle(R.string.success_alert_title)
                                    setMessage(R.string.register_alert_message)
                                    setPositiveButton(R.string.continue_alert_button) { _, _ ->
                                        val intent = Intent(context, LoginActivity::class.java)
                                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                        startActivity(intent)
                                        finish()
                                    }
                                    create()
                                    show()
                                }
                            }

                            is ResultResponse.Error -> {
                                when (result.error) {
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
                                            .setTitle(R.string.register_failed_alert_title)
                                            .setMessage(R.string.register_failed_alert_message)
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
//        ObjectAnimator.ofFloat(registerBind?.logo, View.TRANSLATION_X, -30f, 30f).apply {
//            duration = 6000
//            repeatCount = ObjectAnimator.INFINITE
//            repeatMode = ObjectAnimator.REVERSE
//        }.start()

//        val title = ObjectAnimator.ofFloat(binding?.titleSignup, View.ALPHA, 1f).setDuration(500)
        val nameEditText = ObjectAnimator.ofFloat(binding?.nameEditTextLayout, View.ALPHA, 1f).setDuration(500)
        val emailEditTextLayout = ObjectAnimator.ofFloat(binding?.emailEditTextLayout, View.ALPHA, 1f).setDuration(500)
        val phoneEditTextLayout = ObjectAnimator.ofFloat(binding?.phoneEditTextLayout, View.ALPHA, 1f).setDuration(500)
        val cityEditTextLayout = ObjectAnimator.ofFloat(binding?.cityEditTextLayout, View.ALPHA, 1f).setDuration(500)
        val passwordEditTextLayout = ObjectAnimator.ofFloat(binding?.passwordEditTextLayout, View.ALPHA, 1f).setDuration(500)
        val register = ObjectAnimator.ofFloat(binding?.registerButton, View.ALPHA, 1f).setDuration(500)
        val login = ObjectAnimator.ofFloat(binding?.haveAnAccountBase, View.ALPHA, 1f).setDuration(500)

        AnimatorSet().apply {
//            title,
            playSequentially(nameEditText, emailEditTextLayout, phoneEditTextLayout, cityEditTextLayout, passwordEditTextLayout, register, login)
            startDelay = 500
        }.start()
    }
}