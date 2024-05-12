package com.nabila.griyakulinersreview.ui.editmenu

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.nabila.griyakulinersreview.databinding.ActivityUploadBinding
import com.nabila.griyakulinersreview.ui.home.MainActivity
import com.nabila.griyakulinersreview.util.EXTRA_DESC
import com.nabila.griyakulinersreview.util.EXTRA_ID
import com.nabila.griyakulinersreview.util.EXTRA_NAME
import com.nabila.griyakulinersreview.util.EXTRA_PHOTO
import com.nabila.griyakulinersreview.util.EXTRA_PRICE
import com.nabila.griyakulinersreview.util.UiState
import com.nabila.griyakulinersreview.util.hide
import com.nabila.griyakulinersreview.util.show
import com.nabila.griyakulinersreview.util.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditMenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUploadBinding
    private lateinit var imageUri: Uri
    private val viewModel: EditMenuViewModel by viewModels()
    private var menuId: String? = null
    private var menuPhoto: String? = null
    private var menuName: String? = null
    private var menuDesc: String? = null
    private var price: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setData()
    }

    private fun setData() {
        menuId = intent.getStringExtra(EXTRA_ID)
        menuPhoto = intent.getStringExtra(EXTRA_PHOTO)
        menuName = intent.getStringExtra(EXTRA_NAME)
        menuDesc = intent.getStringExtra(EXTRA_DESC)
        price = intent.getStringExtra(EXTRA_PRICE)

        binding.apply {
            Glide.with(this@EditMenuActivity)
                .load(menuPhoto)
                .into(image)
            edtMenuName.setText(menuName)
            edtPrice.setText(price)
            edtDesc.setText(menuDesc)
            addMenu.text = "Simpan Perubahan"
            addMenu.setOnClickListener { saveMenu() }
            image.setOnClickListener {
                launcherGallery.launch(
                    PickVisualMediaRequest(
                        ActivityResultContracts.PickVisualMedia.ImageOnly
                    )
                )
            }
        }
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            imageUri = uri
            showImage()
        }
    }

    private fun showImage() {
        imageUri.let { binding.image.setImageURI(it) }
    }

    private fun saveMenu() {
        val newMenuName = binding.edtMenuName.text.toString()
        val newMenuDesc = binding.edtDesc.text.toString()
        val newMenuPrice = binding.edtPrice.text.toString()
        if (::imageUri.isInitialized) {
            viewModel.deletePhoto(menuPhoto!!)
            viewModel.editMenuWithPhoto(menuId!!, imageUri, newMenuName, newMenuPrice, newMenuDesc)
            result()
        } else {
            viewModel.editMenu(menuId!!, newMenuName, newMenuPrice, newMenuDesc)
            result()
        }
    }

    private fun result(){
        viewModel.result.observe(this) { state ->
            when (state) {
                is UiState.Loading -> {
                    binding.loading.show()
                }
                is UiState.Failure -> {
                    binding.loading.hide()
                    showToast(state.error!!)
                }
                is UiState.Success -> {
                    binding.loading.hide()
                    showToast(state.data)
                    moveToHome()
                }
            }
        }
    }

    private fun moveToHome() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}