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
        observer()

        binding.apply {
            login.setOnClickListener { login() }
            register.setOnClickListener { moveToRegister() }
        }
    }

    private fun observer(){
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
                    moveToHome()
                }
            }
        }
    }

    private fun login() {
        val email = binding.edtEmail
        val password = binding.edtPassword

        if (validate(this@LoginActivity, email, password)) {
            viewModel.login(
                email.text.toString(),
                password.text.toString()
            )
        }
    }

    private fun moveToRegister() {
        startActivity(Intent(this, RegisterActivity::class.java))
    }

    private fun moveToHome() {
        startActivity(Intent(this, MainActivity::class.java))
    }
}