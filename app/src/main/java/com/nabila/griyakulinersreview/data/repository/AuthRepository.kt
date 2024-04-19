package com.nabila.griyakulinersreview.data.repository

import com.nabila.griyakulinersreview.util.UiState

interface AuthRepository {
    fun register(username: String, email: String, password: String, result: (UiState<String>) -> Unit)
    fun login(email: String, password: String, result: (UiState<String>) -> Unit)
    fun isLoggedIn(): Boolean
}