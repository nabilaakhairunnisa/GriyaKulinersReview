package com.nabila.griyakulinersreview.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.nabila.griyakulinersreview.R
import com.nabila.griyakulinersreview.data.model.MenuMakanan
import com.nabila.griyakulinersreview.data.model.Review
import com.nabila.griyakulinersreview.databinding.ActivityMainBinding
import com.nabila.griyakulinersreview.ui.adapter.MenuAdapter
import com.nabila.griyakulinersreview.ui.login.LoginActivity
import com.nabila.griyakulinersreview.ui.upload.UploadActivity
import com.nabila.griyakulinersreview.util.show
import com.nabila.griyakulinersreview.util.showDialog
import com.nabila.griyakulinersreview.util.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mDatabaseRef: DatabaseReference
    val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        isAdmin()

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("menu")

        binding.rvMenu.layoutManager = LinearLayoutManager (this@MainActivity)

        mDatabaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val menuList = mutableListOf<MenuMakanan>()
                for (menuSnapshot in dataSnapshot.children) {
                    val menuId = menuSnapshot.key
                    val menuName = menuSnapshot.child("menuName").getValue(String::class.java)
                    val price = menuSnapshot.child("price").getValue(String::class.java)
                    val description = menuSnapshot.child("description").getValue(String::class.java)
                    val imageUrl = menuSnapshot.child("imageUrl").getValue(String::class.java)

                    val reviewsSnapshot = menuSnapshot.child("reviews")
                    val reviews = mutableListOf<Review>()
                    for (reviewSnapshot in reviewsSnapshot.children) {
                        val rating = reviewSnapshot.child("rating").getValue(Int::class.java)
                        val reviewer = reviewSnapshot.child("reviewer").getValue(String::class.java)
                        val review = reviewSnapshot.child("review").getValue(String::class.java)
                        if (rating != null && reviewer != null && review != null) {
                            val reviewObj = Review(reviewer, rating, review)
                            reviews.add(reviewObj)
                        }
                    }
                    val menu = MenuMakanan(menuId!!, menuName!!, price!!, description!!, imageUrl!!)
                    menu.let {
                        menuList.add(it)
                    }
                    val adapter = MenuAdapter(menuList.asReversed())
                    binding.rvMenu.adapter = adapter
                }
                binding.loading.visibility = View.GONE
            }

            override fun onCancelled(databaseError: DatabaseError) {
                showToast(getString(R.string.failed_to_retrieve_data))
            }
        })

        binding.apply {
            addMenu.setOnClickListener { moveToUpload() }
            logout.setOnClickListener { logout() }
        }
    }

    private fun isAdmin() {
        if (viewModel.isAdmin()) {
            binding.addMenu.show()
        }
    }

    private fun moveToUpload() {
        startActivity(Intent(this@MainActivity, UploadActivity::class.java))
    }

    private fun logout() {
        showDialog(
            this,
            getString(R.string.logout),


            getString(R.string.logout_confirm),
            positiveButtonAction = {
                viewModel.logout {
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                }
            },
            getString(R.string.logout_success)
        )
    }
}