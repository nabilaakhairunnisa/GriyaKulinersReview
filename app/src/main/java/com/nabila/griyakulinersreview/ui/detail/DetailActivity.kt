package com.nabila.griyakulinersreview.ui.detail

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.nabila.griyakulinersreview.R
import com.nabila.griyakulinersreview.data.model.Review
import com.nabila.griyakulinersreview.data.repository.MenuRepository
import com.nabila.griyakulinersreview.databinding.ActivityDetailBinding
import com.nabila.griyakulinersreview.ui.adapter.ReviewAdapter
import com.nabila.griyakulinersreview.ui.showDialog
import com.nabila.griyakulinersreview.ui.viewmodel.MainViewModel
import com.nabila.griyakulinersreview.ui.viewmodel.ViewModelFactory

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: MainViewModel
    private var databaseRef: DatabaseReference = FirebaseDatabase.getInstance().getReference("menu")
    private var menuId: String? = null
    private var rating = 0
    private val fill = R.drawable.star_fill
    private val outline = R.drawable.star_outline
    private var displayName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val repository = MenuRepository(/* inisialisasi sesuai kebutuhan */)
        val viewModelFactory = ViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        menuId = intent.getStringExtra(EXTRA_ID)
        val menuPhoto = intent.getStringExtra(EXTRA_PHOTO)
        val menuName = intent.getStringExtra(EXTRA_NAME)
        val menuDesc = intent.getStringExtra(EXTRA_DESC)
        val price = intent.getStringExtra(EXTRA_PRICE)
        val user = Firebase.auth.currentUser
        displayName = user!!.displayName

        showLoading(true)
        getReviewList()

        if (user.email == "griyakuliner@gmail.com") {
            binding.btAddReview.visibility = View.GONE
        }

        binding.apply {
            Glide.with(this@DetailActivity)
                .load(menuPhoto)
                .into(image)
            tvMenuName.text = menuName
            desc.text = menuDesc
            tvPrice.text = price
            rvReviews.layoutManager = LinearLayoutManager(this@DetailActivity)

            btAddReview.setOnClickListener {
                showReviewLayout(true)
                star()
            }

            postReviewLayout.reviewLayout.setOnClickListener { showReviewLayout(false) }
            postReviewLayout.btPostReview.setOnClickListener { postReview() }
        }
    }

    private fun postReview() {
        Toast.makeText(this@DetailActivity, displayName, Toast.LENGTH_LONG).show()
        val desc = binding.postReviewLayout.description
        val description = desc.text.toString()
        val newRating = star()
        val review = Review(displayName, newRating, description)

        viewModel.addReview(menuId!!, review)

        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(desc.windowToken, 0)
        desc.setText("")
        rating = 0
        showReviewLayout(false)
        setStar(outline, outline, outline, outline, outline)

        getReviewList()
    }

    private fun getReviewList() {
        viewModel.getReviewList(menuId!!).observe(this@DetailActivity) {
            val adapter = ReviewAdapter(it.asReversed())
            binding.rvReviews.adapter = adapter
            showLoading(false)
        }
    }

    private fun showReviewLayout(show: Boolean) {
        binding.postReviewLayout.reviewLayout.visibility = if (show) View.VISIBLE else View.GONE
    }

    private fun showLoading(show: Boolean) {
        binding.loading.visibility = if (show) View.VISIBLE else View.GONE
    }

    private fun star(): Int {
        binding.postReviewLayout.apply {
            star1.setOnClickListener {
                rating = 1
                setStar(fill, outline, outline, outline, outline)
            }
            star2.setOnClickListener {
                rating = 2
                setStar(fill, fill, outline, outline, outline)
            }
            star3.setOnClickListener {
                rating = 3
                setStar(fill, fill, fill, outline, outline)
            }
            star4.setOnClickListener {
                rating = 4
                setStar(fill, fill, fill, fill, outline)
            }
            star5.setOnClickListener {
                rating = 5
                setStar(fill, fill, fill, fill, fill)
            }
        }
        return rating
    }

    private fun setStar(image1: Int, image2: Int, image3: Int, image4: Int, image5: Int, ) {
        binding.postReviewLayout.apply {
            star1.setImageResource(image1)
            star2.setImageResource(image2)
            star3.setImageResource(image3)
            star4.setImageResource(image4)
            star5.setImageResource(image5)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val user = Firebase.auth.currentUser

        if (user!!.email == "griyakuliner@gmail.com") {
            menuInflater.inflate(R.menu.menu_detail, menu)
        }

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.delete) {
            val menuRef = databaseRef.child(menuId!!)
            showDialog(
                this,
                getString(R.string.delete_menu),
                getString(R.string.delete_confirm),
                positiveButtonAction = {
                    menuRef.removeValue().addOnSuccessListener {
                        finish()
                    }
                },
                getString(R.string.delete_success)
            )
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val EXTRA_ID = "extra_id"
        const val EXTRA_PHOTO = "extra_photo"
        const val EXTRA_NAME = "extra_name"
        const val EXTRA_PRICE = "extra_price"
        const val EXTRA_DESC = "extra_desc"
    }
}