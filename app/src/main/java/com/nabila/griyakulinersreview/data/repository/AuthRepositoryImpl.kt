package com.nabila.griyakulinersreview.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.nabila.griyakulinersreview.util.UiState

class AuthRepositoryImpl (val auth: FirebaseAuth): AuthRepository {

    val user = auth.currentUser

    override fun register(
        username: String,
        email: String,
        password: String,
        result: (UiState<String>) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    result.invoke(
                        UiState.Success("Akun Berhasil Dibuat")
                    )
                }
            }
            .addOnFailureListener {
                result.invoke(
                    UiState.Failure(
                        it.localizedMessage
                    )
                )
            }
    }

    override fun login(
        email: String,
        password: String,
        result: (UiState<String>) -> Unit
    ) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    result.invoke(
                        UiState.Success("Berhasil Login")
                    )
                }
            }
            .addOnFailureListener {
                result.invoke(
                    UiState.Failure(
                        it.localizedMessage
                    )
                )
            }
    }

    override fun isLoggedIn(): Boolean {
        var isLoggedIn = false
        if (auth.currentUser != null) {
            isLoggedIn = true
        }
        return isLoggedIn
    }

}