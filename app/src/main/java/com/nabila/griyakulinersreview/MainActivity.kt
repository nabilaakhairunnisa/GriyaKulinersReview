package com.nabila.griyakulinersreview

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.nabila.griyakulinersreview.data.model.MenuMakanan
import com.nabila.griyakulinersreview.databinding.ActivityMainBinding
import com.nabila.griyakulinersreview.ui.adapter.MenuAdapter
import com.nabila.griyakulinersreview.ui.editusername.EditUsernameActivity
import com.nabila.griyakulinersreview.ui.login.LoginActivity
import com.nabila.griyakulinersreview.ui.showDialog
import com.nabila.griyakulinersreview.ui.upload.UploadActivity
import com.nabila.griyakulinersreview.ui.viewmodel.MainViewModel
import com.nabila.griyakulinersreview.ui.viewmodel.ViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels { ViewModelFactory.getInstance(this) }
    private var mDatabaseRef: DatabaseReference = FirebaseDatabase.getInstance().getReference("menu")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getSession().observe(this) { user ->
            if (!user.isLogin) {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            } else if (user.username != "griya123") {
                binding.addMenu.visibility = View.GONE
            }
        }

        mDatabaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val menuList = mutableListOf<MenuMakanan>()

                for (snapshot in dataSnapshot.children) {
                    val menu = snapshot.getValue(MenuMakanan::class.java)
                    menu?.let {
                        menuList.add(it)
                        menuList.reverse()
                    }
                }
                val adapter = MenuAdapter(menuList)
                binding.rvMenu.adapter = adapter
                binding.loading.visibility = View.GONE
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(this@MainActivity, "Failed to retrieve data", Toast.LENGTH_SHORT).show()
            }
        })

        binding.apply {
            rvMenu.layoutManager = LinearLayoutManager (this@MainActivity)
            addMenu.setOnClickListener {
                startActivity(Intent(this@MainActivity, UploadActivity::class.java))
            }
        }

        viewModel.getMenuList().observe(this) { menuList ->
            val adapter = MenuAdapter(menuList)
            binding.rvMenu.adapter = adapter
            binding.loading.visibility = View.GONE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.edit_username) {
            startActivity(Intent(this, EditUsernameActivity::class.java))
        } else if (item.itemId == R.id.logout) {
            showDialog(
                this,
                getString(R.string.logout),
                getString(R.string.logout_confirm),
                positiveButtonAction = {
                    viewModel.logout()
                    finish()
                },
                getString(R.string.logout_success)
            )
        }
        return super.onOptionsItemSelected(item)
    }
}

//class MainActivity : AppCompatActivity() {
//
//    private lateinit var binding: ActivityMainBinding
//    private val viewModel: MainViewModel by viewModels { ViewModelFactory.getInstance(this) }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        binding.apply {
//            rvMenu.layoutManager = LinearLayoutManager (this@MainActivity)
//            addMenu.setOnClickListener {
//                startActivity(Intent(this@MainActivity, UploadActivity::class.java))
//            }
//        }
//
//        viewModel.getSession().observe(this) { user ->
//            if (!user.isLogin) {
//                startActivity(Intent(this, LoginActivity::class.java))
//                finish()
//            }
//        }
//
//        viewModel.getMenuList().observe(this) { menuList ->
//            val adapter = MenuAdapter(menuList)
//            binding.rvMenu.adapter = adapter
//            binding.loading.visibility = View.GONE
//        }
//    }
//
//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.menu_main, menu)
//        return super.onCreateOptionsMenu(menu)
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        return super.onOptionsItemSelected(item)
//    }
//}