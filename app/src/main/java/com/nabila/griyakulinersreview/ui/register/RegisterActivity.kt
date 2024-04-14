package com.nabila.griyakulinersreview.ui.register

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.nabila.griyakulinersreview.R
import com.nabila.griyakulinersreview.databinding.ActivityRegisterBinding
import com.nabila.griyakulinersreview.ui.login.LoginActivity
import com.nabila.griyakulinersreview.ui.showToast
import com.nabila.griyakulinersreview.ui.transparentStatusBar

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var  auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        supportActionBar?.hide()
        transparentStatusBar(this)

        binding.apply {
            register.setOnClickListener {
                val email = edtEmail.text.toString()
                val username = binding.edtUsername.text.toString()
                val password = edtPassword.text.toString()

                if (email.isNotEmpty() && password.isNotEmpty()) {
                    auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                        if (it.isSuccessful) {
                            val user = FirebaseAuth.getInstance().currentUser
                            val profileUpdates = UserProfileChangeRequest.Builder()
                                .setDisplayName(username)
                                .build()

                            user?.updateProfile(profileUpdates)?.addOnCompleteListener { updateTask ->
                                if (updateTask.isSuccessful) {
                                    val currentUser = FirebaseAuth.getInstance().currentUser
                                    val displayName = currentUser?.displayName

                                    Toast.makeText(
                                        this@RegisterActivity,
                                        "Create Account Success with username: $displayName",
                                        Toast.LENGTH_LONG
                                    ).show()

                                    startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                                    finish()
                                } else {
                                    Toast.makeText(
                                        this@RegisterActivity,
                                        updateTask.exception?.localizedMessage,
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            }
                        }
                    }.addOnFailureListener {
                        Toast.makeText(
                            this@RegisterActivity,
                            it.localizedMessage,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                } else {
                    showToast(this@RegisterActivity, R.string.empty_email_password)
                }
            }

            login.setOnClickListener{
                startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                finish()
            }
        }
    }
}