package com.nabila.griyakulinersreview.data.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.FirebaseDatabase
import com.nabila.griyakulinersreview.util.UiState

class AuthRepositoryImpl (val auth: FirebaseAuth, val database: FirebaseDatabase): AuthRepository {

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
                    result.invoke(UiState.Success("Akun Berhasil Dibuat, Silahkan Login"))
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
        username: String,
        email: String,
        password: String,
        result: (UiState<String>) -> Unit
    ) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    result.invoke(
                        UiState.Success("Login Berhasil")
                    )
                    setDisplayName(username)
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

    override fun setDisplayName(username: String) {
        val profileUpdate = UserProfileChangeRequest.Builder()
            .setDisplayName(username)
            .build()

        user?.updateProfile(profileUpdate)
            ?.addOnCompleteListener { updateTask ->
                if (updateTask.isSuccessful) {
                    val displayName = user.displayName
                    Log.d("SetDisplayName", "Berhasil Menyimpan Username: $displayName")
                }
            }
    }

    override fun isLoggedIn(): Boolean {
        var isLoggedIn = false
        if (user != null) {
            isLoggedIn = true
        }
        return isLoggedIn
    }



    override fun isAdmin(): Boolean {
        var isAdmin = false
        if (isLoggedIn()) {
            if (user!!.email == "griyakuliner@gmail.com") {
                isAdmin = true
            }
        }
        return isAdmin
    }

    override fun logout(result: () -> Unit) {
        auth.signOut()
        result.invoke()
    }

}