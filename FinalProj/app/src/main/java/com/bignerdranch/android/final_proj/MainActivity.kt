package com.bignerdranch.android.final_proj

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
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

class MainActivity : ComponentActivity() {

    private lateinit var actionButton: Button
    private lateinit var scoreText: TextView
    private var score = 0
    private var timer: CountDownTimer? = null
    private val actions = listOf("BOP IT", "TWIST IT", "PULL IT")
    private var timeLeftMillis: Long = 0L
    private lateinit var timerProgress: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout)

        actionButton = findViewById(R.id.actionButton)
        scoreText = findViewById(R.id.scoreText)
        timerProgress = findViewById(R.id.timerProgress)
        timerProgress.max = 3000
        timerProgress.progress = 3000

        startGame()
        enableEdgeToEdge()
    }

    private fun startGame() {
        showNextAction()
    }

    private fun showNextAction() {
        val nextAction = actions.random()
        actionButton.text = nextAction
        timerProgress.progress = 3000

        moveButtonToRandomPosition()

        // Animate the button
        animateButton()

        actionButton.visibility = View.VISIBLE

        timer?.cancel()
        timer = object : CountDownTimer(3000, 50) {
            override fun onTick(millisUntilFinished: Long) {
                timeLeftMillis = millisUntilFinished
                timerProgress.progress = millisUntilFinished.toInt()
            }
            override fun onFinish() {
                timeLeftMillis = 0L
                endGame()
            }
        }.start()

        actionButton.setOnClickListener {
            timer?.cancel()

            val maxTime = 3000L
            val bonus = (timeLeftMillis.toDouble() / maxTime * 10).toInt() // max 10 points
            score += bonus.coerceAtLeast(1) // ensure at least 1 point

            scoreText.text = "Score: $score"
            showNextAction()
        }
    }

    private fun moveButtonToRandomPosition() {
        val rootLayout = findViewById<LinearLayout>(R.id.rootLayout)

        // Get layout dimensions
        rootLayout.post {
            val layoutWidth = rootLayout.width
            val layoutHeight = rootLayout.height

            // Get button size
            val buttonWidth = actionButton.width
            val buttonHeight = actionButton.height

            val maxX = layoutWidth - buttonWidth
            val maxY = layoutHeight - buttonHeight

            val randomX = (0..maxX).random()
            val randomY = (0..maxY).random()

            actionButton.x = randomX.toFloat()
            actionButton.y = randomY.toFloat()
        }
    }

    private fun animateButton() {
        actionButton.scaleX = 0f
        actionButton.scaleY = 0f
        actionButton.alpha = 0f

        actionButton.animate()
            .scaleX(1f)
            .scaleY(1f)
            .alpha(1f)
            .setDuration(300)
            .start()
    }

    private fun endGame() {
        actionButton.visibility = View.INVISIBLE
        actionButton.setOnClickListener(null)
        Toast.makeText(this, "Game Over! Score: $score", Toast.LENGTH_LONG).show()
    }

}