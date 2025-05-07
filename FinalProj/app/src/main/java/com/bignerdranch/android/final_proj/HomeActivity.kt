package com.bignerdranch.android.final_proj

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.ComponentActivity

class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val startButton: Button = findViewById(R.id.startButton)
        startButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val instructionsButton: Button = findViewById(R.id.instructionsButton)
        instructionsButton.setOnClickListener {
            Toast.makeText(this, "Tap, Shake, or Hold as fast as you can!", Toast.LENGTH_LONG).show()
        }
    }
}