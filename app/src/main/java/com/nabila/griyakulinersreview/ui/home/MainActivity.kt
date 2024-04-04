package com.nabila.griyakulinersreview.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
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
import com.nabila.griyakulinersreview.ui.editusername.EditUsernameActivity
import com.nabila.griyakulinersreview.ui.login.LoginActivity
import com.nabila.griyakulinersreview.ui.showDialog
import com.nabila.griyakulinersreview.ui.showToast
import com.nabila.griyakulinersreview.ui.upload.UploadActivity
import com.nabila.griyakulinersreview.ui.viewmodel.MainViewModel
import com.nabila.griyakulinersreview.ui.viewmodel.ViewModelFactory


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels { ViewModelFactory.getInstance(this) }
    private lateinit var mDatabaseRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("menu")

        viewModel.getSession().observe(this) { user ->
            if (user.username == "griya123") {
                binding.addMenu.visibility = View.VISIBLE
            }
        }

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
                showToast(this@MainActivity, R.string.failed_to_retrieve_data)
            }
        })

        binding.apply {
            addMenu.setOnClickListener {
                startActivity(Intent(this@MainActivity, UploadActivity::class.java))
            }
        }
    }

    private fun calculateAverageRating(reviews: MutableList<Review>): Double {
        var totalRating = 0
        var numReviews = 0
        for (review in reviews) {
            totalRating += review.rating!!
            numReviews++
        }
        return if (numReviews > 0) {
            val averageRating = totalRating.toFloat() / numReviews.toFloat()
            "%.1f".format(averageRating).toDouble()
        } else {
            0.0
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
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                },
                getString(R.string.logout_success)
            )

        }
        return super.onOptionsItemSelected(item)
    }
}