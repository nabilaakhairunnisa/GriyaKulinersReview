package com.nabila.griyakulinersreview.ui

import android.app.Activity
import android.content.Context
import android.os.Build
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.nabila.griyakulinersreview.R

fun showDialog (
    context: Context,
    title: String,
    message: String,
    positiveButtonAction: () -> Unit,
    successText: String
    ) {
    val alertDialog = AlertDialog.Builder(context)
    alertDialog.setTitle(title)
    alertDialog.setMessage(message)
    alertDialog.setPositiveButton(context.getString(R.string.yes)) { dialog, _ ->
        positiveButtonAction()
        Toast.makeText(context, successText, Toast.LENGTH_SHORT).show()
        dialog.dismiss()
    }
    alertDialog.setNegativeButton(context.getString(R.string.no)) { dialog, _ ->
        dialog.dismiss()
    }
    alertDialog.create().show()
}

fun showToast (context: Context, text: Int) {
    Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
}

fun statusBarIconsColor(activity: Activity) {
    if (Build.VERSION.SDK_INT >= 23) {
        val decor = activity.window.decorView
        if (decor.systemUiVisibility and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR != View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR) {
            decor.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        } else {
            decor.systemUiVisibility = 0
        }
    }
}

fun transparantStatusBar(activity: Activity) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
        val w = activity.window
        w.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
    }
}