package com.nabila.griyakulinersreview.ui.upload

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nabila.griyakulinersreview.data.repository.MenuRepository
import com.nabila.griyakulinersreview.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UploadViewModel @Inject constructor(val repository: MenuRepository) : ViewModel() {

    private val _result = MutableLiveData<UiState<String>>()
    val result: LiveData<UiState<String>>
        get() = _result

    fun addMenu(
        imageUri: Uri,
        menuName: String,
        menuPrice: String,
        menuDesc: String
    ) {
        _result.value = UiState.Loading
        repository.addMenu(
            imageUri,
            menuName,
            menuPrice,
            menuDesc
        ) {
            _result.value = it
        }
    }
}