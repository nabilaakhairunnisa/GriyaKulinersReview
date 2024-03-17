package com.nabila.griyakulinersreview.ui.editmenu

import android.os.Bundle
import android.text.Editable
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.nabila.griyakulinersreview.R
import com.nabila.griyakulinersreview.data.model.MenuMakanan
import com.nabila.griyakulinersreview.databinding.ActivityUploadBinding
import com.nabila.griyakulinersreview.ui.detail.DetailActivity.Companion.EXTRA_DESC
import com.nabila.griyakulinersreview.ui.detail.DetailActivity.Companion.EXTRA_ID
import com.nabila.griyakulinersreview.ui.detail.DetailActivity.Companion.EXTRA_NAME
import com.nabila.griyakulinersreview.ui.detail.DetailActivity.Companion.EXTRA_PHOTO
import com.nabila.griyakulinersreview.ui.detail.DetailActivity.Companion.EXTRA_PRICE

class EditMenuActivity: AppCompatActivity() {

    private lateinit var binding: ActivityUploadBinding
    private var menuRef: DatabaseReference = FirebaseDatabase.getInstance().getReference("menu")

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityUploadBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val menuId = intent.getStringExtra(EXTRA_ID)
        val menuName = intent.getStringExtra(EXTRA_NAME)
        val menuDesc = intent.getStringExtra(EXTRA_DESC)
        val price = intent.getStringExtra(EXTRA_PRICE)
        val menuPhoto = intent.getStringExtra(EXTRA_PHOTO)

        binding.apply {
            tvMenuName.text = menuName?.let { Editable.Factory.getInstance().newEditable(it) }
            tvDesc.text = menuDesc?.let { Editable.Factory.getInstance().newEditable(it) }
            tvPrice.text = price?.let { Editable.Factory.getInstance().newEditable(it) }
            Glide.with(this@EditMenuActivity)
                .load(menuPhoto)
                .into(ivImage)
            btAddMenu.text = getString(R.string.save_changes)
            btAddMenu.backgroundTintList = ContextCompat.getColorStateList(this@EditMenuActivity, R.color.dark_green)
            btAddMenu.setTextColor(resources.getColor(R.color.white))

            val newMenuName = tvMenuName.text.toString()
            val newMenuDesc = tvDesc.text.toString()
            val newPrice = tvPrice.text.toString()

            val menu = MenuMakanan(menuId!!, newMenuName, newPrice, newMenuDesc, menuPhoto!!)

            btAddMenu.setOnClickListener {
                menuRef.child(menuId).setValue(menu)
                Toast.makeText(this@EditMenuActivity, "Perubahan Berhasil Disimpan", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}