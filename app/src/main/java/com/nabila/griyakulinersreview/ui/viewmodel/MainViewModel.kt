package com.nabila.griyakulinersreview.ui.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.nabila.griyakulinersreview.data.model.MenuMakanan
import com.nabila.griyakulinersreview.data.model.Review
import com.nabila.griyakulinersreview.data.repository.MenuRepository
import kotlinx.coroutines.launch

class MainViewModel(private val repository: MenuRepository) : ViewModel() {

    fun addMenu(menuName: String, description: String, imageUri: Uri) {
        viewModelScope.launch {
            val imageUrl = repository.uploadImage(imageUri)
            repository.addMenu(menuName, description, imageUrl)
        }
    }

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