package com.example.personaltasks.ui

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.personaltasks.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {
    private lateinit var viewBinding : ActivityRegisterBinding
    private val auth by lazy {
        FirebaseAuth.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)


        viewBinding.btnRegister.setOnClickListener{

            val email = viewBinding.etEmail.text
            val password = viewBinding.etPassword.text
            val confirmPassword = viewBinding.etConfirmPassword.text

            if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()){
                Toast.makeText(this,"all fields must be filled" , Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(confirmPassword.toString() != password.toString()){
                Toast.makeText(this,"the passwords must be equal", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(!Patterns.EMAIL_ADDRESS.matcher(email.toString()).matches()){
                Toast.makeText(this,"invalid email",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            auth.createUserWithEmailAndPassword(email.toString(),password.toString()).addOnCompleteListener(this){
                signUp->
                if(signUp.isSuccessful){
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
                else{
                    Toast.makeText(this,"error ${signUp.exception?.message}",Toast.LENGTH_SHORT).show()
                    println(signUp.exception?.message)
                }
            }

        }
        val tvGoToLogin = viewBinding.tvGoToLogin
        tvGoToLogin.setOnClickListener{
            startActivity(Intent(this,LoginActivity::class.java))
            finish()
        }
    }

}
