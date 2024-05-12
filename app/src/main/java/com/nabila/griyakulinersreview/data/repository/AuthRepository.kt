package com.nabila.griyakulinersreview.data.repository

import androidx.lifecycle.LiveData
import com.nabila.griyakulinersreview.util.UiState

interface AuthRepository {
    fun register(username: String, email: String, password: String, result: (UiState<String>) -> Unit)
    fun login(email: String, password: String, result: (UiState<String>) -> Unit)
    fun setDisplayName(username: String, result: (UiState<String>) -> Unit)
    fun getDisplayName(): LiveData<String>
    fun isLoggedIn(): Boolean
    fun isAdmin(): Boolean
    fun logout()
}