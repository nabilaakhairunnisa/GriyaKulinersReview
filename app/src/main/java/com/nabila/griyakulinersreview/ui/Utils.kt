package com.nabila.griyakulinersreview.ui

import android.content.Context
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