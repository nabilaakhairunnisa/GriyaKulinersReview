package com.nabila.griyakulinersreview.util

import android.content.Context
import android.widget.EditText
import android.widget.Toast
import com.nabila.griyakulinersreview.R

object ValidateInput {
    fun validate(context: Context, email: EditText, password: EditText): Boolean {
        var isValid = true

        if (email.text.isNullOrEmpty()){
            isValid = false
            showToast(context, R.string.enter_email)
        } else {
            if (!email.text.toString().isValidEmail()){
                isValid = false
                showToast(context, R.string.invalid_email)
            }
        }
        if (password.text.isNullOrEmpty()){
            isValid = false
            showToast(context, R.string.enter_password)
        } else {
            if (password.text.toString().length < 6){
                isValid = false
                showToast(context, R.string.invalid_password)
            }
        }
        return isValid
    }

    private fun showToast(context: Context, text: Int) =
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
}