package com.nabila.griyakulinersreview.util

import android.app.Activity
import android.content.Context
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

fun Context.showToast (text: String) =
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()

fun transparentStatusBar(activity: Activity) {
    val w = activity.window
    w.setFlags(
        WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
        WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
    )
}

fun String.isValidEmail() =
    isNotEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()

fun View.show(){
    visibility = View.VISIBLE
}

fun View.hide(){
    visibility = View.GONE
}