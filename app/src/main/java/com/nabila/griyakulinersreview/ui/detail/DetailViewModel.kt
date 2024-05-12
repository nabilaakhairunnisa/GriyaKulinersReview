package com.nabila.griyakulinersreview.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.nabila.griyakulinersreview.data.model.Review
import com.nabila.griyakulinersreview.data.repository.AuthRepository
import com.nabila.griyakulinersreview.data.repository.MenuRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    val authRepository: AuthRepository,
    val menuRepository: MenuRepository
    ): ViewModel() {

    fun isAdmin(): Boolean {
        return authRepository.isAdmin()
    }

    fun reviewList(menuId: String): LiveData<List<Review>> {
        return menuRepository.getReviewList(menuId)
    }

    fun addReview(menuId: String, review: Review) {
        menuRepository.addReview(menuId, review)
    }

    fun deleteMenu(menuId: String) {
        menuRepository.deleteMenu(menuId)
    }

    fun deletePhoto(imageUrl: String) {
        menuRepository.deletePhoto(imageUrl)
    }
}