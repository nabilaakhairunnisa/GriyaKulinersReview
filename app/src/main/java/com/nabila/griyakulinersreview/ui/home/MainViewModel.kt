package com.nabila.griyakulinersreview.ui.home

import androidx.lifecycle.ViewModel
import com.nabila.griyakulinersreview.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(val repository: AuthRepository) : ViewModel() {

    fun isAdmin(): Boolean {
        val isAdmin = repository.isAdmin()
        return isAdmin
    }
    fun logout(result: () -> Unit) = repository.logout(result)
}