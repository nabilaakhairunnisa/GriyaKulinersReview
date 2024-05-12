package com.nabila.griyakulinersreview.ui.detail

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.nabila.griyakulinersreview.R
import com.nabila.griyakulinersreview.data.model.Review
import com.nabila.griyakulinersreview.databinding.ActivityDetailBinding
import com.nabila.griyakulinersreview.ui.adapter.ReviewAdapter
import com.nabila.griyakulinersreview.ui.editmenu.EditMenuActivity
import com.nabila.griyakulinersreview.util.EXTRA_DESC
import com.nabila.griyakulinersreview.util.EXTRA_ID
import com.nabila.griyakulinersreview.util.EXTRA_NAME
import com.nabila.griyakulinersreview.util.EXTRA_PHOTO
import com.nabila.griyakulinersreview.util.EXTRA_PRICE
import com.nabila.griyakulinersreview.util.USERNAME
import com.nabila.griyakulinersreview.util.hide
import com.nabila.griyakulinersreview.util.hideKeyboard
import com.nabila.griyakulinersreview.util.outline
import com.nabila.griyakulinersreview.util.setStar
import com.nabila.griyakulinersreview.util.show
import com.nabila.griyakulinersreview.util.showDialog
import com.nabila.griyakulinersreview.util.star
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val viewModel: DetailViewModel by viewModels()
    private var menuId: String? = null
    private var menuPhoto: String? = null
    private var menuName: String? = null
    private var menuDesc: String? = null
    private var menuPrice: String? = null
    private var username: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        menuId = intent.getStringExtra(EXTRA_ID)
        menuPhoto = intent.getStringExtra(EXTRA_PHOTO)
        menuName = intent.getStringExtra(EXTRA_NAME)
        menuDesc = intent.getStringExtra(EXTRA_DESC)
        menuPrice = intent.getStringExtra(EXTRA_PRICE)
        username = intent.getStringExtra(USERNAME)

        isAdmin()
        getDetailMenu()
        getReviewList()
    }

    private fun getDetailMenu() {
        binding.apply {
            Glide.with(this@DetailActivity)
                .load(menuPhoto)
                .into(photo)
            name.text = menuName
            desc.text = menuDesc
            price.text = menuPrice
            review.bg.setOnClickListener { review.bg.hide() }
            review.addReview.setOnClickListener { addReview() }
            editMenu.setOnClickListener { moveToEdit() }
            delete.setOnClickListener { deleteMenu() }
            addReview.setOnClickListener {
                review.bg.show()
                star(binding.review)
            }
        }
    }


    private fun isAdmin() {
        binding.apply {
            if (!viewModel.isAdmin()) {
                delete.hide()
                editMenu.hide()
            }
        }
    }

    private fun moveToEdit() {
        val intent = Intent(this, EditMenuActivity::class.java)
        intent.putExtra(EXTRA_ID, menuId)
        intent.putExtra(EXTRA_NAME, menuName)
        intent.putExtra(EXTRA_PRICE, menuPrice)
        intent.putExtra(EXTRA_DESC, menuDesc)
        intent.putExtra(EXTRA_PHOTO, menuPhoto)
        startActivity(intent)
    }

    private fun addReview() {
        val desc = binding.review.desc.text.toString()
        val rating = star(binding.review)
        val review = Review(username, rating, desc)
        viewModel.addReview(menuId!!, review)

        hideKeyboard(binding.review.desc)
        binding.review.desc.setText("")
        binding.review.bg.hide()
        setStar(binding.review, outline, outline, outline, outline, outline)
    }

    private fun getReviewList() {
        binding.reviews.layoutManager = LinearLayoutManager (this)
        binding.loading.show()
        viewModel.reviewList(menuId!!).observe(this) { data ->
            val adapter = ReviewAdapter(data)
            binding.reviews.adapter = adapter
            binding.loading.hide()
        }
    }

    private fun deleteMenu() {
        showDialog(
            this,
            getString(R.string.delete_menu),
            getString(R.string.delete_confirm),
            positiveButtonAction = {
                viewModel.deletePhoto(menuPhoto!!)
                viewModel.deleteMenu(menuId!!)
                finish()
            },
            getString(R.string.delete_success)

        )
    }
}