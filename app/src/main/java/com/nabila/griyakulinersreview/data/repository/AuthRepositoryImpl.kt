package com.nabila.griyakulinersreview.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.nabila.griyakulinersreview.data.model.User
import com.nabila.griyakulinersreview.util.UiState

class AuthRepositoryImpl (private val auth: FirebaseAuth, private val db: FirebaseDatabase): AuthRepository {
    override fun register(
        username: String,
        email: String,
        password: String,
        result: (UiState<String>) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val uid = task.result?.user?.uid
                    if (uid != null) {
                        val user = User (uid, username, email)
                        db.getReference("users").child(uid).setValue(user)
                        result.invoke(UiState.Success("Akun Berhasil Dibuat, Silahkan Login"))
                    }
                }
            } .addOnFailureListener { result.invoke(UiState.Failure(it.localizedMessage))
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
                        UiState.Success("Login Berhasil")
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

    override fun setDisplayName(username: String, result: (UiState<String>) -> Unit) {
        val user = auth.currentUser
        if (user != null) {
            val uid = user.uid
            db.getReference("users").child(uid).child("username").setValue(username)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        result.invoke(UiState.Success("Username Berhasil Diubah"))
                    }
                }.addOnFailureListener {
                    result.invoke(
                        UiState.Failure(
                            it.localizedMessage
                        )
                    )
                }
        }
    }

    override fun getDisplayName(): LiveData<String> {
        val username = MutableLiveData<String>()
        val user = auth.currentUser
        if (user != null) {
            val uid = user.uid
            db.getReference("users").child(uid)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        username.value = snapshot.child("username").value.toString()
                    }
                    override fun onCancelled(error: DatabaseError) {
                        Log.d("getDisplayName:", error.toString())
                    }
                })
        }
        return username
    }

    override fun isLoggedIn(): Boolean {
        return auth.currentUser != null
    }

    override fun isAdmin(): Boolean {
        return auth.currentUser?.email == "griyakuliner@gmail.com"
    }

    override fun logout() {
        auth.signOut()
    }
}