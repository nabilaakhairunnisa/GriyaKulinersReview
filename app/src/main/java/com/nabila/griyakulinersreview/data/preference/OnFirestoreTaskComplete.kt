package com.nabila.griyakulinersreview.data.preference

import com.nabila.griyakulinersreview.data.model.MenuMakanan

interface OnFirestoreTaskComplete {
    fun quizDataLoaded(menuMakanan: List<MenuMakanan?>?)
    fun onError(e: Exception?)
}