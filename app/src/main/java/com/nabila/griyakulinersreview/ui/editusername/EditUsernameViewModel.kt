package com.nabila.griyakulinersreview.ui.editusername

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nabila.griyakulinersreview.data.repository.AuthRepository
import com.nabila.griyakulinersreview.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EditUsernameViewModel @Inject constructor(val authRepository: AuthRepository): ViewModel() {

    val _result = MutableLiveData<UiState<String>>()
    val result: LiveData<UiState<String>> get() = _result

    fun editUsername(username: String) {
        _result.value = UiState.Loading
        authRepository.setDisplayName(username) {
            _result.value = it
        }
    }
}