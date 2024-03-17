package com.nabila.griyakulinersreview.ui.detail

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.nabila.griyakulinersreview.R
import com.nabila.griyakulinersreview.data.model.Review
import com.nabila.griyakulinersreview.databinding.ActivityDetailBinding
import com.nabila.griyakulinersreview.ui.adapter.ReviewAdapter
import com.nabila.griyakulinersreview.ui.showDialog
import com.nabila.griyakulinersreview.ui.showToast
import com.nabila.griyakulinersreview.ui.viewmodel.MainViewModel
import com.nabila.griyakulinersreview.ui.viewmodel.ViewModelFactory

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val viewModel: MainViewModel by viewModels { ViewModelFactory.getInstance(this) }
    private var databaseRef: DatabaseReference = FirebaseDatabase.getInstance().getReference("menu")
    private var menuId: String? = null
    private var rating = 0
    private var user = ""
    private val fill = R.drawable.star_fill
    private val outline = R.drawable.star_outline

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        menuId = intent.getStringExtra(EXTRA_ID)
        val menuPhoto = intent.getStringExtra(EXTRA_PHOTO)
        val menuName = intent.getStringExtra(EXTRA_NAME)
        val menuDesc = intent.getStringExtra(EXTRA_DESC)
        val price = intent.getStringExtra(EXTRA_PRICE)
        showLoading(true)
        getReviewList()

        viewModel.getSession().observe(this@DetailActivity) { user ->
            this.user = user.username
            if (user.username == "griya123") {
                binding.btAddReview.visibility = View.GONE
            }
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
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        binding.postReviewLayout.description.apply {
            val desc = text.toString()
            val newRating = star()
            val review = Review(user, newRating, desc)
            viewModel.addReview(menuId!!, review)
            setText("")
            inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
        }
        rating = 0
        showReviewLayout(false)
        setStar(outline, outline, outline, outline, outline)
        getReviewList()
    }

    private fun getReviewList() {
        databaseRef.child(menuId!!).child("reviews").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val reviewList = mutableListOf<Review>()
                for (reviewSnapshot in snapshot.children) {
                    val review = reviewSnapshot.getValue(Review::class.java)
                    review?.let {
                        reviewList.add(it)
                    }
                }
                val adapter = ReviewAdapter(reviewList.asReversed())
                binding.rvReviews.adapter = adapter
            }

            override fun onCancelled(error: DatabaseError) {
                showToast(this@DetailActivity, R.string.failed_to_retrieve_data)
            }

        })
        showLoading(false)
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
        viewModel.getSession().observe(this) { user ->
            if (user.username == "griya123") {
                menuInflater.inflate(R.menu.menu_detail, menu)
            }
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