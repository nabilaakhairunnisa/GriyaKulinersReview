package com.nabila.griyakulinersreview.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.nabila.griyakulinersreview.R
import com.nabila.griyakulinersreview.databinding.ActivityMainBinding
import com.nabila.griyakulinersreview.ui.adapter.MenuAdapter
import com.nabila.griyakulinersreview.ui.editusername.EditUsernameActivity
import com.nabila.griyakulinersreview.ui.login.LoginActivity
import com.nabila.griyakulinersreview.ui.upload.UploadActivity
import com.nabila.griyakulinersreview.util.hide
import com.nabila.griyakulinersreview.util.show
import com.nabila.griyakulinersreview.util.showDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        isLoggedIn()
        isAdmin()
        getMenuList()

        binding.apply {
            addMenu.setOnClickListener { moveToUpload() }
            logout.setOnClickListener { logout() }
            editUsername.setOnClickListener { moveToEdit() }
        }
    }



    private fun getMenuList() {
        binding.rvMenu.layoutManager = LinearLayoutManager (this)
        viewModel.getUsername.observe(this) { username ->
            binding.loading.show()
            viewModel.menuList.observe(this) { data ->
                val adapter = MenuAdapter(data, username)
                binding.rvMenu.adapter = adapter
                binding.loading.hide()
            }
        }
    }

    private fun logout() {
        showDialog(
            this,
            getString(R.string.logout),
            getString(R.string.logout_confirm),
            positiveButtonAction = {
                viewModel.logout()
                moveToLogin()
            },
            getString(R.string.logout_success)
        )
    }

    private fun isLoggedIn() {
        if (!viewModel.isLoggedIn()) moveToLogin()
    }

    private fun isAdmin() {
        if (!viewModel.isAdmin()) binding.addMenu.hide()
    }

    private fun moveToEdit() {
        startActivity(Intent(this, EditUsernameActivity::class.java))
    }

    private fun moveToUpload() {
        startActivity(Intent(this, UploadActivity::class.java))
    }

    private fun moveToLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
    }
}