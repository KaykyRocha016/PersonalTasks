package com.example.personaltasks.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.personaltasks.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivityLoginBinding;
    private val auth by lazy {
        FirebaseAuth.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        auth.currentUser?.let {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
        val btnLogin = viewBinding.btnLogin
        btnLogin.setOnClickListener {
            val email = viewBinding.etEmail.text
            val password = viewBinding.etPassword.text
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "email and password are required!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            auth.signInWithEmailAndPassword(email.toString().trim(), password.toString())
                .addOnCompleteListener(this) { login ->
                    if (login.isSuccessful) {
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(this, "incorrect password or email!", Toast.LENGTH_SHORT).show()
                    }
                };

        }
        val btnSignUp = viewBinding.btnSignUp
        btnSignUp.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }


    }


}