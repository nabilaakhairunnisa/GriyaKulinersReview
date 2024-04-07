package com.nabila.griyakulinersreview.data.repository

import android.net.Uri
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.nabila.griyakulinersreview.data.model.MenuMakanan
import com.nabila.griyakulinersreview.data.model.Review
import com.nabila.griyakulinersreview.data.model.User
import com.nabila.griyakulinersreview.data.preference.MenuPreference
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class MenuRepository(private val preference: MenuPreference) {

    fun getSession(): Flow<User> {
        return preference.getSession()
    }

    suspend fun saveSession(user: User) {
        preference.saveSession(user)
    }

    suspend fun logout() {
        preference.logout()
        instance = null
    }

    fun getThemeSetting(): Flow<Boolean> {
        return preference.getThemeSetting()
    }

    suspend fun saveThemeSetting (isDarkModeActive: Boolean) {
        preference.saveThemeSetting(isDarkModeActive)
    }

    fun addMenu(menuName: String, description: String, imageUrl: String) {
        val menuId = databaseRef.push().key
        val menu = MenuMakanan(menuId!!, menuName, description, imageUrl)
        databaseRef.child(menuId).setValue(menu)
    }

    suspend fun uploadImage(imageUri: Uri): String {
        return suspendCoroutine { continuation ->
            val fileReference: StorageReference = storageRef.child(System.currentTimeMillis().toString())
            fileReference.putFile(imageUri)
                .addOnSuccessListener { taskSnapshot ->
                    taskSnapshot.storage.downloadUrl.addOnSuccessListener { uri ->
                        val imageUrl = uri.toString()
                        continuation.resume(imageUrl)
                    }
                }
                .addOnFailureListener { e ->
                    continuation.resumeWithException(e)
                }
        }
    }

    fun getMenuList(): Flow<List<MenuMakanan>> {
        return flow {
            val dataSnapshot = databaseRef.get().await()
            val menuList = mutableListOf<MenuMakanan>()
            for (snapshot in dataSnapshot.children) {
                val menu = snapshot.getValue(MenuMakanan::class.java)
                menu?.let { menuList.add(it) }
            }
            emit(menuList)
        }
    }

    fun addReview(menuId: String, review: Review) {
        val reviewId = databaseRef.push().key
        databaseRef.child(menuId).child("reviews").child(reviewId!!).setValue(review)
    }

    fun getReviewList(menuId: String): Flow<List<Review>> {
        return flow {
            val dataSnapshot = databaseRef.child(menuId).child("reviews").get().await()
            val reviews = mutableListOf<Review>()
            for (snapshot in dataSnapshot.children) {
                val review = snapshot.getValue(Review::class.java)
                review?.let { reviews.add(it) }
            }
            emit(reviews)
        }
    }

    companion object {
        @Volatile
        private var instance: MenuRepository? = null
        val storageRef: StorageReference
        val databaseRef: DatabaseReference

        init {
            storageRef = FirebaseStorage.getInstance().getReference("foto makanan")
            databaseRef = FirebaseDatabase.getInstance().getReference("menu")
        }

        fun getInstance(pref: MenuPreference) =
            instance ?: synchronized(this) {
                instance ?: MenuRepository(pref)
            }.also { instance = it }
    }
}