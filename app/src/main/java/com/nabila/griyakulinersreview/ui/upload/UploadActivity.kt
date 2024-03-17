package com.nabila.griyakulinersreview.ui.upload

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.nabila.griyakulinersreview.R
import com.nabila.griyakulinersreview.data.model.MenuMakanan
import com.nabila.griyakulinersreview.databinding.ActivityUploadBinding
import com.nabila.griyakulinersreview.ui.viewmodel.MainViewModel
import com.nabila.griyakulinersreview.ui.viewmodel.ViewModelFactory

class UploadActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUploadBinding
    private lateinit var currentImageUri: Uri
    private val viewModel: MainViewModel by viewModels { ViewModelFactory.getInstance(this) }
    private lateinit var mStorageRef: StorageReference
    private lateinit var mDatabaseRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mStorageRef = FirebaseStorage.getInstance().getReference("foto makanan")
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("menu")

        binding.btAddMenu.setOnClickListener {
            showLoading(true)
            addMenu()
        }

        binding.ivImage.setOnClickListener {
            launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
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
                            val menu = MenuMakanan(menuId, menuName, price, description, imageUrl)
                            mDatabaseRef.child(menuId).setValue(menu)
                                .addOnSuccessListener {
                                    showToast(R.string.upload_successful)
                                    finish()
                                }
                                .addOnFailureListener { e ->
                                    showToastError(R.string.upload_failed, e.message)
                                }
                        } else {
                            showToast(R.string.failed_id)
                        }
                    }
                }
                .addOnFailureListener { e ->
                    showToastError(R.string.upload_failed, e.message)
                }
        } else {
            showToast(R.string.image_unselected)
        }
    }

    private fun showToastError(uploadFailed: Int, message: String?) {
//        Toast.makeText(this, getString(uploadFailed, message), Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(show: Boolean) {
        binding.loading.visibility = if (show) View.VISIBLE else View.GONE
    }

    private fun showToast(text: Int) {
        Toast.makeText(this, getString(text), Toast.LENGTH_SHORT).show()
    }
}

//class UploadActivity : AppCompatActivity() {
//
//    private lateinit var binding: ActivityUploadBinding
//    private lateinit var currentImageUri: Uri
//    private val viewModel: MainViewModel by viewModels { ViewModelFactory.getInstance(this) }
//    private lateinit var mStorageRef: StorageReference
//    private lateinit var mDatabaseRef: DatabaseReference
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityUploadBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        mStorageRef = FirebaseStorage.getInstance().getReference("foto makanan")
//        mDatabaseRef = FirebaseDatabase.getInstance().getReference("menu")
//
//        binding.btAddMenu.setOnClickListener {
//            addMenu()
//            binding.loading.visibility = View.VISIBLE
//        }
//
//        binding.image.setOnClickListener {
//            launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
//        }
//    }
//
//    private val launcherGallery = registerForActivityResult(
//        ActivityResultContracts.PickVisualMedia()
//    ) { uri: Uri? ->
//        if (uri != null) {
//            currentImageUri = uri
//            showImage()
//        } else {
//            Log.d("Photo Picker", "No media selected")
//        }
//    }
//
//    private fun showImage() {
//        currentImageUri.let { binding.image.setImageURI(it) }
//    }
//
//    private fun addMenu() {
//        val menuName = binding.menuName.text.toString()
//        val description = binding.desc.text.toString()
//
//        if (::currentImageUri.isInitialized) {
//            viewModel.addMenu(menuName, description, currentImageUri)
//        } else {
//            showToast(R.string.image_unselected)
//        }
//    }
//
//    private fun showToast(text: Int) {
//        Toast.makeText(this, getString(text), Toast.LENGTH_SHORT).show()
//    }
//}


