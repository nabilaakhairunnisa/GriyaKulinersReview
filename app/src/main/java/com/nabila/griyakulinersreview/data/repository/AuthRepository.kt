package com.nabila.griyakulinersreview.data.repository

import com.nabila.griyakulinersreview.util.UiState

interface AuthRepository {
    fun register(username: String, email: String, password: String, result: (UiState<String>) -> Unit)
    fun login(username: String, email: String, password: String, result: (UiState<String>) -> Unit)
    fun setDisplayName(username: String)
    fun isLoggedIn(): Boolean
    fun isAdmin(): Boolean
    fun logout(result: () -> Unit)
}