package com.nabila.griyakulinersreview.util

import com.nabila.griyakulinersreview.R
import com.nabila.griyakulinersreview.databinding.AddReviewBinding

val fill = R.drawable.star_fill
val outline = R.drawable.star_outline
var rating = 0

fun star(view: AddReviewBinding): Int {

    view.apply {
        star1.setOnClickListener {
            rating = 1
            setStar(view, fill, outline, outline, outline, outline)
        }
        star2.setOnClickListener {
            rating = 2
            setStar(view, fill, fill, outline, outline, outline)
        }
        star3.setOnClickListener {
            rating = 3
            setStar(view, fill, fill, fill, outline, outline)
        }
        star4.setOnClickListener {
            rating = 4
            setStar(view, fill, fill, fill, fill, outline)
        }
        star5.setOnClickListener {
            rating = 5
            setStar(view, fill, fill, fill, fill, fill)
        }
    }
    return rating
}

fun setStar(view: AddReviewBinding, image1: Int, image2: Int, image3: Int, image4: Int, image5: Int) {
    view.apply {
        star1.setImageResource(image1)
        star2.setImageResource(image2)
        star3.setImageResource(image3)
        star4.setImageResource(image4)
        star5.setImageResource(image5)
    }
}