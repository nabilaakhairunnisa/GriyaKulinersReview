package com.nabila.griyakulinersreview.data.repository

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.Tasks
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.nabila.griyakulinersreview.data.model.MenuMakanan
import com.nabila.griyakulinersreview.data.model.Review
import com.nabila.griyakulinersreview.util.UiState

class MenuRepositoryImpl(private val db: FirebaseDatabase, private val storage: FirebaseStorage): MenuRepository {

    val menuRef = db.getReference("menu")

    override fun getMenuList(): LiveData<List<MenuMakanan>> {
        val data = MutableLiveData<List<MenuMakanan>>()
        menuRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val menuList = mutableListOf<MenuMakanan>()
                for (menuSnapshot in dataSnapshot.children) {
                    val menu = menuSnapshot.getValue(MenuMakanan::class.java)
                    menu?.let { menuList.add(it) }
                }
                data.value = menuList.asReversed()
            }
            override fun onCancelled(databaseError: DatabaseError) {
                Log.d("getMenuList:", databaseError.toString())
            }
        })
        return data
    }

    override fun getReviewList(menuId: String): LiveData<List<Review>> {
        val data = MutableLiveData<List<Review>>()
        menuRef.child(menuId).child("reviews").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val menuList = mutableListOf<Review>()
                for (menuSnapshot in dataSnapshot.children) {
                    val menu = menuSnapshot.getValue(Review::class.java)
                    menu?.let { menuList.add(it) }
                }
                data.value = menuList.asReversed()
            }
            override fun onCancelled(databaseError: DatabaseError) {
                Log.d("getReviewList:", databaseError.toString())
            }
        })
        return data
    }

    override fun editMenu(
        menuId: String,
        newMenuName: String,
        newMenuPrice: String,
        newMenuDesc: String,
        result: (UiState<String>) -> Unit
    ) {
        val menu = menuRef.child(menuId)
        val menuNameTask = menu.child("menuName").setValue(newMenuName)
        val priceTask = menu.child("price").setValue(newMenuPrice)
        val descriptionTask = menu.child("description").setValue(newMenuDesc)

        val allTasks = listOf(menuNameTask, priceTask, descriptionTask)

        Tasks.whenAllComplete(allTasks)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    result.invoke(UiState.Success("Perubahan Berhasil Disimpan"))
                }
            }.addOnFailureListener {
                result.invoke(UiState.Failure(it.localizedMessage))
            }
    }

    override fun editMenuWithPhoto(
        menuId: String,
        imageUri: Uri,
        newMenuName: String,
        newMenuPrice: String,
        newMenuDesc: String,
        result: (UiState<String>) -> Unit
    ) {
        storage.getReference("foto makanan")
            .child(System.currentTimeMillis().toString())
            .putFile(imageUri).addOnSuccessListener { taskSnapshot ->
                taskSnapshot.storage.downloadUrl.addOnSuccessListener { uri ->
                    val menuPhoto = uri.toString()
                    val menu = menuRef.child(menuId)
                    val imageUrlTask = menu.child("imageUrl").setValue(menuPhoto)
                    val menuNameTask = menu.child("menuName").setValue(newMenuName)
                    val priceTask = menu.child("price").setValue(newMenuPrice)
                    val descriptionTask = menu.child("description").setValue(newMenuDesc)
                    val allTasks = listOf(imageUrlTask, menuNameTask, priceTask, descriptionTask)
                    Tasks.whenAllComplete(allTasks)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                result.invoke(UiState.Success("Perubahan Berhasil Disimpan"))
                            }
                        }.addOnFailureListener {
                            result.invoke(UiState.Failure(it.localizedMessage))
                        }
                }
            }
    }

    override fun addMenu(
        imageUri: Uri,
        menuName: String,
        menuPrice: String,
        menuDesc: String,
        result: (UiState<String>) -> Unit
    ) {
        storage.getReference("foto makanan")
            .child(System.currentTimeMillis().toString())
            .putFile(imageUri).addOnSuccessListener { taskSnapshot ->
                taskSnapshot.storage.downloadUrl.addOnSuccessListener { uri ->
                    val menuPhoto = uri.toString()
                    val menuId = menuRef.push().key
                    if (menuId != null) {
                        val menu = MenuMakanan(menuId, menuName, menuPrice, menuDesc, menuPhoto)
                        menuRef.child(menuId).setValue(menu)
                            .addOnSuccessListener {
                                result.invoke(UiState.Success("Menu Berhasil Ditambahkan"))
                            }.addOnFailureListener {
                                result.invoke(UiState.Failure(it.localizedMessage))
                            }
                    }
                }.addOnFailureListener {
                    result.invoke(UiState.Failure(it.localizedMessage))
                }
            }
    }

    override fun addReview(menuId: String, review: Review) {
        val reviewId = menuRef.push().key
        if (reviewId != null) {
            menuRef.child(menuId).child("reviews").child(reviewId).setValue(review)
        }
    }

    override fun deleteMenu(menuId: String) {
        menuRef.child(menuId).removeValue()
    }

    override fun deletePhoto(imageUrl: String) {
        storage.getReferenceFromUrl(imageUrl).delete()
    }
}