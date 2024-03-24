package com.nabila.griyakulinersreview.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.nabila.griyakulinersreview.data.model.User
import com.nabila.griyakulinersreview.databinding.ActivityLoginBinding
import com.nabila.griyakulinersreview.ui.home.MainActivity
import com.nabila.griyakulinersreview.ui.viewmodel.MainViewModel
import com.nabila.griyakulinersreview.ui.viewmodel.ViewModelFactory

class LoginActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels { ViewModelFactory.getInstance(this) }
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        viewModel.getSession().observe(this) { user ->
//            if (user.isLogin) {
//                startActivity(Intent(this, MainActivity::class.java))
//                finish()
//            }
//        }

        supportActionBar?.hide()

        binding.login.setOnClickListener {
            val username = binding.username.text.toString()
            viewModel.saveSession(User(username, true))
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}