package com.nabila.griyakulinersreview.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nabila.griyakulinersreview.data.repository.AuthRepository
import com.nabila.griyakulinersreview.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(val repository: AuthRepository): ViewModel() {
    private val _register = MutableLiveData<UiState<String>>()
    val registerState: LiveData<UiState<String>>
        get() = _register

    fun register(username: String, email: String, password: String) {
        _register.value = UiState.Loading
        repository.register(username, email, password){
            _register.value = it
        }
    }
}