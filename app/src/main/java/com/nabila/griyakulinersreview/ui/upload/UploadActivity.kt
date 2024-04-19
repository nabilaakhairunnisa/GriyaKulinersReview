package com.nabila.griyakulinersreview.ui.upload

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.nabila.griyakulinersreview.R
import com.nabila.griyakulinersreview.data.model.MenuMakanan
import com.nabila.griyakulinersreview.databinding.ActivityUploadBinding
import com.nabila.griyakulinersreview.util.showToast

class UploadActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUploadBinding
    private lateinit var currentImageUri: Uri
    private lateinit var mStorageRef: StorageReference
    private lateinit var mDatabaseRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mStorageRef = FirebaseStorage.getInstance().getReference("foto makanan")
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("menu")

        binding.apply {
            btAddMenu.setOnClickListener {
                loading.visibility = View.VISIBLE
                addMenu()
            }

            ivImage.setOnClickListener {
                launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            }
        }
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            showImage()
        } else {
            Log.d("Photo Picker", "No media selected")
        }
    }

    private fun showImage() {
        currentImageUri.let { binding.ivImage.setImageURI(it) }
    }

    private fun addMenu() {
        val menuName = binding.tvMenuName.text.toString()
        val price = binding.tvPrice.text.toString()
        val description = binding.tvDesc.text.toString()

        if (::currentImageUri.isInitialized) {
            val fileReference: StorageReference = mStorageRef.child(System.currentTimeMillis().toString())
            fileReference.putFile(currentImageUri)
                .addOnSuccessListener { taskSnapshot ->
                    taskSnapshot.storage.downloadUrl.addOnSuccessListener { uri ->
                        val imageUrl = uri.toString()
                        val menuId = mDatabaseRef.push().key
                        if (menuId != null) {
                            val menu = MenuMakanan(menuId, menuName, price, description, imageUrl, 0.0)
                            mDatabaseRef.child(menuId).setValue(menu)
                                .addOnSuccessListener {
                                    showToast(getString(R.string.upload_successful))
                                    finish()
                                }
                        } else {
                            showToast(getString( R.string.failed_id))
                        }
                    }
                }
        } else {
            showToast(getString( R.string.image_unselected))
        }
    }
}

