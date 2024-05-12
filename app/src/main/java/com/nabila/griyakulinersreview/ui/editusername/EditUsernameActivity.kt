package com.nabila.griyakulinersreview.ui.editusername

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.nabila.griyakulinersreview.databinding.ActivityEditUsernameBinding
import com.nabila.griyakulinersreview.ui.home.MainActivity
import com.nabila.griyakulinersreview.util.UiState
import com.nabila.griyakulinersreview.util.hide
import com.nabila.griyakulinersreview.util.show
import com.nabila.griyakulinersreview.util.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditUsernameActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditUsernameBinding
    private val viewModel: EditUsernameViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditUsernameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        observer()

        binding.save.setOnClickListener { saveNewUsername() }
    }

    private fun saveNewUsername() {
        binding.loading.show()
        val newUsername = binding.edtUsername.text.toString()
        viewModel.editUsername(newUsername)
    }

    private fun observer(){
        viewModel.result.observe(this) { state ->
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

    private fun moveToHome() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}