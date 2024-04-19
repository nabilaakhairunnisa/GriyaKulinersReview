package com.nabila.griyakulinersreview.data.repository

import android.net.Uri
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.nabila.griyakulinersreview.data.model.MenuMakanan
import com.nabila.griyakulinersreview.data.model.Review
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class MenuRepository {

    fun addMenu(menuName: String, price: String, description: String, imageUri: Uri) {
        val fileReference: StorageReference = storageRef.child(System.currentTimeMillis().toString())
        fileReference.putFile(imageUri)
            .addOnSuccessListener { takeSnapshot ->
                takeSnapshot.storage.downloadUrl.addOnSuccessListener { uri ->
                    val imageUrl = uri.toString()
                    val menuId = databaseRef.push().key
                    if (menuId != null) {
                        val menu = MenuMakanan(menuId, menuName, price, description, imageUrl, 0.0)
                        databaseRef.child(menuId).setValue(menu)
                            .addOnSuccessListener {
                            }
                            .addOnFailureListener {
                            }
                    } else {

                    }
                }
            }
            .addOnFailureListener {

            }
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
        val storageRef: StorageReference = FirebaseStorage.getInstance().getReference("foto makanan")
        val databaseRef: DatabaseReference = FirebaseDatabase.getInstance().getReference("menu")
    }
}