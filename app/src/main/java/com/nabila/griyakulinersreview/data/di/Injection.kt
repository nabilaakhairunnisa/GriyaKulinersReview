package com.nabila.griyakulinersreview.data.di

import android.content.Context
import com.nabila.griyakulinersreview.data.preference.MenuPreference
import com.nabila.griyakulinersreview.data.preference.dataStore
import com.nabila.griyakulinersreview.data.repository.MenuRepository

object Injection {
    fun provideRepository(context: Context): MenuRepository {
        val pref = MenuPreference.getInstance(context.dataStore)
        return MenuRepository.getInstance(pref)
    }
}
