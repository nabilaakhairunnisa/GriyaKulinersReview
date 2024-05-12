package com.nabila.griyakulinersreview.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.nabila.griyakulinersreview.data.model.MenuMakanan
import com.nabila.griyakulinersreview.data.repository.AuthRepository
import com.nabila.griyakulinersreview.data.repository.MenuRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val repository: AuthRepository,
    menuRepository: MenuRepository
) : ViewModel() {

    val getUsername: LiveData<String> = repository.getDisplayName()

    val menuList: LiveData<List<MenuMakanan>> = menuRepository.getMenuList()

    fun isLoggedIn(): Boolean {
        return repository.isLoggedIn()
    }

    fun isAdmin(): Boolean {
        return repository.isAdmin()
    }

    fun logout() = repository.logout()
}