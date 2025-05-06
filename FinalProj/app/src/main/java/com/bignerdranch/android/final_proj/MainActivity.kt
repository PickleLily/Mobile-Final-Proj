package com.bignerdranch.android.final_proj

//import android.app.Activity
import android.os.Bundle
//import android.util.Log
//import android.view.View
//import androidx.activity.result.contract.ActivityResultContracts
//import androidx.activity.viewModels
//import android.content.Intent
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.bignerdranch.android.final_proj.ui.theme.FinalProjTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val startButton = findViewById<Button>(R.id.start_button)
        val localLeaderboardButton = findViewById<Button>(R.id.local_button)
        val globalLeaderboardButton = findViewById<Button>(R.id.global_button)


// This should start the game?
        startButton.setOnClickListener{
//            ...
//            val intentStart = Intent(this, GameActivity::class.java)
//            startActivity(intentStart)
        }
//        this should change screens and display a local leaderboard (aka your top scores)
        localLeaderboardButton.setOnClickListener{
//            ...
        }
//        this should change screens and display a global leaderboard (aka the scores of everyone who has played the game!)
        globalLeaderboardButton.setOnClickListener{
//            ...
        }
        enableEdgeToEdge()
        setContent {
            FinalProjTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
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