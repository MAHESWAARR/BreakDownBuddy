package com.example.androidproject_1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class NotifyScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_notify_screen)


        val allowbutton = findViewById<Button>(R.id.button_allow)
        allowbutton.setOnClickListener {
            Toast.makeText(this,"Notifications enabled", Toast.LENGTH_SHORT).show()
            val explicitintent = Intent(this, LoginScreen::class.java)
            startActivity(explicitintent)
            finish()
        }

        val dontallowbutton = findViewById<Button>(R.id.button_dont_allow)
        dontallowbutton.setOnClickListener {
            Toast.makeText(this, "Notification Not enabled", Toast.LENGTH_SHORT).show()
            val explicitintent = Intent(this, LoginScreen::class.java)
            startActivity(explicitintent)
            finish()
        }
    }
}