package com.nabila.griyakulinersreview.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.nabila.griyakulinersreview.R
import com.nabila.griyakulinersreview.data.model.Review
import com.nabila.griyakulinersreview.databinding.ItemReviewBinding

class ReviewAdapter(private val reviewList: List<Review>) : RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>() {

    inner class ReviewViewHolder(private val binding: ItemReviewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(review: Review) {
            binding.apply {
                reviewer.text = review.reviewer
                ulasan.text = review.review
                setStarImage(star1, review.rating!! >= 1)
                setStarImage(star2, review.rating >= 2)
                setStarImage(star3, review.rating >= 3)
                setStarImage(star4, review.rating >= 4)
                setStarImage(star5, review.rating >= 5)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val binding = ItemReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReviewViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        val review = reviewList[position]
        holder.bind(review)
    }

    override fun getItemCount(): Int {
        return reviewList.size
    }

    private fun setStarImage(imageView: ImageView, isFilled: Boolean) {
        if (isFilled) {
            imageView.setImageResource(R.drawable.star_fill)
        }
    }
}
