package com.nabila.griyakulinersreview.ui.register

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.nabila.griyakulinersreview.databinding.ActivityRegisterBinding
import com.nabila.griyakulinersreview.ui.login.LoginActivity
import com.nabila.griyakulinersreview.util.ValidateInput.validate
import com.nabila.griyakulinersreview.util.transparentStatusBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val viewModel: RegisterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        transparentStatusBar(this)

        binding.apply {
            register.setOnClickListener {

                val username = edtUsername
                val email = edtEmail
                val password = edtPassword

                if (validate(this@RegisterActivity, email, password)) {
                    viewModel.register(
                        username = username.text.toString(),
                        email = email.text.toString(),
                        password = password.text.toString()
                    )
                }


//                    auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
//                        if (it.isSuccessful) {
//                            val user = FirebaseAuth.getInstance().currentUser
//                            val profileUpdates = UserProfileChangeRequest.Builder()
//                                .setDisplayName(username)
//                                .build()
//
//                            user?.updateProfile(profileUpdates)?.addOnCompleteListener { updateTask ->
//                                if (updateTask.isSuccessful) {
//                                    val currentUser = FirebaseAuth.getInstance().currentUser
//                                    val displayName = currentUser?.displayName
//
//                                    Toast.makeText(
//                                        this@RegisterActivity,
//                                        "Create Account Success with username: $displayName",
//                                        Toast.LENGTH_LONG
//                                    ).show()
//
//                                    startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
//                                    finish()
//                                } else {
//                                    Toast.makeText(
//                                        this@RegisterActivity,
//                                        updateTask.exception?.localizedMessage,
//                                        Toast.LENGTH_LONG
//                                    ).show()
//                                }
//                            }
//                        }
//                    }.addOnFailureListener {
//                        Toast.makeText(
//                            this@RegisterActivity,
//                            it.localizedMessage,
//                            Toast.LENGTH_LONG
//                        ).show()
//                    }
//                } else {
//                    showToast(getString(R.string.empty_email_password))
//                }
            }

            login.setOnClickListener{
                startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                finish()
            }
        }
    }
}