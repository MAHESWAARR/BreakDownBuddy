package com.example.androidproject_1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.AuthResult
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.OnFailureListener

class RegistrationScreen : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var etName: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var etConfirmPassword: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration_screen)

        auth = FirebaseAuth.getInstance()
        etName = findViewById(R.id.namereg)
        etEmail = findViewById(R.id.emailreg)
        etPassword = findViewById(R.id.passwordreg)
        etConfirmPassword = findViewById(R.id.confirmpasswordreg)

        val registerButton = findViewById<Button>(R.id.btnRegister)
        registerButton.setOnClickListener {
            val name = etName.text.toString()
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()
            val confirmPassword = etConfirmPassword.text.toString()

            if (password == confirmPassword) {
                createUserWithEmailAndPassword(email, password)
            } else {
                Toast.makeText(this, "Confirm Password not matched", Toast.LENGTH_SHORT).show()
                // Handle password mismatch
            }
        }
    }

    private fun createUserWithEmailAndPassword(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener(object : OnSuccessListener<AuthResult> {
                override fun onSuccess(authResult: AuthResult) {
                    // User creation successful
                    val intent = Intent(this@RegistrationScreen, HomeScreen::class.java)
                    startActivity(intent)
                    finish()
                }
            })
            .addOnFailureListener(object : OnFailureListener {
                override fun onFailure(exception: Exception) {
                    // Handle user creation failure
                }
            })
    }
}