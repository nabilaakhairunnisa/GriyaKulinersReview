package com.nabila.griyakulinersreview.ui.editmenu

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nabila.griyakulinersreview.data.repository.MenuRepository
import com.nabila.griyakulinersreview.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EditMenuViewModel @Inject constructor(val repository: MenuRepository): ViewModel() {

    val _result = MutableLiveData<UiState<String>>()
    val result: LiveData<UiState<String>> get() = _result

    fun editMenu(
        menuId: String,
        newMenuName: String,
        newMenuPrice: String,
        newMenuDesc: String,
    ) {
        _result.value = UiState.Loading
        repository.editMenu(
            menuId,
            newMenuName,
            newMenuPrice,
            newMenuDesc,
        ) {
            _result.value = it
        }
    }

    fun editMenuWithPhoto(
        menuId: String,
        imageUri: Uri,
        newMenuName: String,
        newMenuPrice: String,
        newMenuDesc: String,
    ) {
        _result.value = UiState.Loading
        repository.editMenuWithPhoto(
            menuId,
            imageUri,
            newMenuName,
            newMenuPrice,
            newMenuDesc,
        ) {
            _result.value = it
        }
    }

    fun deletePhoto(imageUrl: String) {
        repository.deletePhoto(imageUrl)
    }

}