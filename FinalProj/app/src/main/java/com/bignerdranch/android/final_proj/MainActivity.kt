package com.bignerdranch.android.final_proj

import android.os.Bundle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.bignerdranch.android.final_proj.ui.theme.FinalProjTheme
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.core.content.edit

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val title = findViewById<TextView>(R.id.game_title)
        val inputField = findViewById<EditText>(R.id.inputField)
        val saveButton = findViewById<Button>(R.id.saveButton)
        val outputView = findViewById<TextView>(R.id.outputView)

        val sharedPref = getSharedPreferences("MyAppPrefs", MODE_PRIVATE)

        // Load stored value on start
        val savedText = sharedPref.getString("userInput", "Nothing saved yet")
        outputView.text = savedText


        saveButton.setOnClickListener {
            val inputText = inputField.text.toString()
            sharedPref.edit { putString("userInput", inputText) }
            outputView.text = inputText
        }

        enableEdgeToEdge()
        Log.d("MainActivity", "enableEdgeToEdge Set")
    }


    @Composable
    fun Greeting(name: String, modifier: Modifier = Modifier) {
        Text(
            text = "Hello $name!",
            modifier = modifier
        )
    }

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        FinalProjTheme {
            Greeting("Android")
        }
    }
}