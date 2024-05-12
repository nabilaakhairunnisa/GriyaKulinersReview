package com.nabila.griyakulinersreview.data.repository

import android.net.Uri
import androidx.lifecycle.LiveData
import com.nabila.griyakulinersreview.data.model.MenuMakanan
import com.nabila.griyakulinersreview.data.model.Review
import com.nabila.griyakulinersreview.util.UiState

interface MenuRepository {
    fun getMenuList(): LiveData<List<MenuMakanan>>
    fun getReviewList(menuId: String): LiveData<List<Review>>
    fun editMenu(
        menuId: String,
        newMenuName: String,
        newMenuPrice: String,
        newMenuDesc: String,
        result: (UiState<String>) -> Unit
    )
    fun editMenuWithPhoto(
        menuId: String,
        imageUri: Uri,
        newMenuName: String,
        newMenuPrice: String,
        newMenuDesc: String,
        result: (UiState<String>) -> Unit
    )
    fun addMenu(
        imageUri: Uri,
        menuName: String,
        menuPrice: String,
        menuDesc: String,
        result: (UiState<String>) -> Unit
    )
    fun addReview(menuId: String, review: Review)
    fun deleteMenu(menuId: String)
    fun deletePhoto(imageUrl: String)
}