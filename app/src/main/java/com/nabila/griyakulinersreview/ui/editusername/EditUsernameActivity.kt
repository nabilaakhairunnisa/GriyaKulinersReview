package com.nabila.griyakulinersreview.ui.editusername

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.nabila.griyakulinersreview.MainActivity
import com.nabila.griyakulinersreview.R
import com.nabila.griyakulinersreview.data.model.User
import com.nabila.griyakulinersreview.databinding.ActivityLoginBinding
import com.nabila.griyakulinersreview.ui.viewmodel.MainViewModel
import com.nabila.griyakulinersreview.ui.viewmodel.ViewModelFactory

class EditUsernameActivity: AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewmodel: MainViewModel by viewModels { ViewModelFactory.getInstance(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.apply {
            login.text = getString(R.string.save_changes)
            username.hint = "Masukkan username baru"

            login.setOnClickListener {
                val username = username.text.toString()
                viewmodel.saveSession(User(username))
                startActivity(Intent(this@EditUsernameActivity, MainActivity::class.java))
                finish()
            }
        }
    }
}