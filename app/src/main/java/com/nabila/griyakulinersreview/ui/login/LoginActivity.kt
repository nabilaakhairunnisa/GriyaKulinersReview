package com.nabila.griyakulinersreview.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.nabila.griyakulinersreview.R
import com.nabila.griyakulinersreview.databinding.ActivityLoginBinding
import com.nabila.griyakulinersreview.ui.home.MainActivity
import com.nabila.griyakulinersreview.ui.register.RegisterActivity
import com.nabila.griyakulinersreview.ui.showToast
import com.nabila.griyakulinersreview.ui.transparentStatusBar

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var  auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        if (auth.currentUser != null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        supportActionBar?.hide()
        transparentStatusBar(this)

        binding.apply {
            login.setOnClickListener {
                val email = edtEmail.text.toString()
                val password = edtPassword.text.toString()

                if(email.isNotEmpty() && password.isNotEmpty()) {
                    auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                        if(it.isSuccessful) {
                            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                            finish()
                        }
                    }.addOnFailureListener {
                        Toast.makeText(this@LoginActivity, it.localizedMessage, Toast.LENGTH_LONG).show()
                    }
                } else {
                    showToast(this@LoginActivity, R.string.empty_email_password)
                }
            }

            register.setOnClickListener{
                startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
                finish()
            }
        }
    }
}