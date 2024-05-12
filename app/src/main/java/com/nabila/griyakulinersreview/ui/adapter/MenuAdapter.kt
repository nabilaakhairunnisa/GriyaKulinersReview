package com.nabila.griyakulinersreview.ui.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nabila.griyakulinersreview.data.model.MenuMakanan
import com.nabila.griyakulinersreview.databinding.ItemMenuBinding
import com.nabila.griyakulinersreview.ui.detail.DetailActivity
import com.nabila.griyakulinersreview.util.EXTRA_DESC
import com.nabila.griyakulinersreview.util.EXTRA_ID
import com.nabila.griyakulinersreview.util.EXTRA_NAME
import com.nabila.griyakulinersreview.util.EXTRA_PHOTO
import com.nabila.griyakulinersreview.util.EXTRA_PRICE
import com.nabila.griyakulinersreview.util.USERNAME

class MenuAdapter(private val menuList: List<MenuMakanan>, private val username: String) : RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {

    inner class MenuViewHolder(private val binding: ItemMenuBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(menu: MenuMakanan) {
            binding.apply {
                menuName.text = menu.menuName
                desc.text = menu.description
                tvPrice.text = "Rp. " + menu.price
                Glide.with(itemView)
                    .load(menu.imageUrl)
                    .into(image)
                itemMenu.setOnClickListener {
                    val intent = Intent(it.context, DetailActivity::class.java)
                    intent.apply {
                        putExtra(EXTRA_ID, menu.id)
                        putExtra(EXTRA_PHOTO, menu.imageUrl)
                        putExtra(EXTRA_NAME, menu.menuName)
                        putExtra(EXTRA_DESC, menu.description)
                        putExtra(EXTRA_PRICE, menu.price)
                        putExtra(USERNAME, username)
                    }
                    itemView.context.startActivity(
                        intent, ActivityOptionsCompat.makeSceneTransitionAnimation(
                            itemView.context as Activity
                        ).toBundle()
                    )
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val binding = ItemMenuBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MenuViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        val menu = menuList[position]
        holder.bind(menu)
    }

    override fun getItemCount(): Int {
        return menuList.size
    }
}
