package com.nabila.griyakulinersreview.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nabila.griyakulinersreview.data.repository.AuthRepository
import com.nabila.griyakulinersreview.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(val repository: AuthRepository) : ViewModel() {

    private val _login = MutableLiveData<UiState<String>>()
    val loginState: LiveData<UiState<String>>
        get() = _login

    fun login(email: String, password: String) {
        _login.value = UiState.Loading
        repository.login(email, password){
            _login.value = it
        }
    }

}