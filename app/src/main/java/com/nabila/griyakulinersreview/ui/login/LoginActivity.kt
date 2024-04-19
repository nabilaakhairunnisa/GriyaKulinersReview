package com.nabila.griyakulinersreview.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.nabila.griyakulinersreview.databinding.ActivityLoginBinding
import com.nabila.griyakulinersreview.ui.home.MainActivity
import com.nabila.griyakulinersreview.ui.register.RegisterActivity
import com.nabila.griyakulinersreview.util.UiState
import com.nabila.griyakulinersreview.util.ValidateInput.validate
import com.nabila.griyakulinersreview.util.hide
import com.nabila.griyakulinersreview.util.show
import com.nabila.griyakulinersreview.util.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        observer()
        isLoggedIn()

        binding.apply {
            login.setOnClickListener { login() }
            register.setOnClickListener{ moveToRegister() }
        }
    }

    private fun isLoggedIn() {
        if (viewModel.isLoggedIn()) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    private fun login() {
        binding.apply {
            val username = intent.getStringExtra("USERNAME")
            val email = edtEmail
            val password = edtPassword

            showToast(username.toString())

            if (validate(this@LoginActivity, email, password)) {
                viewModel.login(
                    username = username.toString(),
                    email = email.text.toString(),
                    password = password.text.toString()
                )
            }
        }
    }

    private fun moveToRegister() {
        startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
        finish()
    }

    fun observer(){
        viewModel.loginState.observe(this) { state ->
            when (state) {
                is UiState.Loading -> {
                    binding.loading.show()
                }
                is UiState.Failure -> {
                    binding.loading.hide()
                    showToast(state.error!!)
                }
                is UiState.Success -> {
                    binding.loading.hide()
                    showToast(state.data)
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
            }
        }
    }
}