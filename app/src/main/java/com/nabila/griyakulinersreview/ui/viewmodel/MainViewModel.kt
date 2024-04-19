package com.nabila.griyakulinersreview.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.nabila.griyakulinersreview.data.model.MenuMakanan
import com.nabila.griyakulinersreview.data.model.Review
import com.nabila.griyakulinersreview.data.repository.MenuRepository

class MainViewModel(private val repository: MenuRepository) : ViewModel() {
//    fun login(email: String, password: String) {
//        repository.login(email, password)
//    }

    fun getMenuList(): LiveData<List<MenuMakanan>> {
        return repository.getMenuList().asLiveData()
    }

    fun addReview(menuId: String, review: Review) {
        repository.addReview(menuId, review)
    }

    fun getReviewList(menuId: String): LiveData<List<Review>> {
        return repository.getReviewList(menuId).asLiveData()
    }
}