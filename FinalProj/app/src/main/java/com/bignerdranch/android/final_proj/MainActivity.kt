package com.bignerdranch.android.final_proj

import android.content.Context
import android.content.res.ColorStateList
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.core.content.ContextCompat
import kotlin.math.sqrt

class MainActivity : ComponentActivity() {

    // Button structure
    private lateinit var actionButton: Button
    private val actions = listOf("PRESS IT", "PUSH IT", "TAP IT", "DON'T TAP IT", "SHAKE IT")

    // Score count
    private lateinit var scoreText: TextView
    private var score = 0

    // Timer
    private var timer: CountDownTimer? = null // Timer
    private var timeLeftMillis: Long = 0L // Timer value / countdown
    private lateinit var timerProgress: ProgressBar // Actual timer display

    // Detection for "Shake it"
    private lateinit var sensorManager: SensorManager
    private var accelCurrent = 0f
    private var accelLast = 0f
    private var shake = 0f
    private var expectingShake = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout)

        actionButton = findViewById(R.id.actionButton)
        scoreText = findViewById(R.id.scoreText)
        timerProgress = findViewById(R.id.timerProgress)
        timerProgress.max = 3000
        timerProgress.progress = 3000
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelCurrent = SensorManager.GRAVITY_EARTH
        accelLast = SensorManager.GRAVITY_EARTH

        startGame()
        enableEdgeToEdge()
    }

    private val sensorListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent) {
            val x = event.values[0]
            val y = event.values[1]
            val z = event.values[2]

            accelLast = accelCurrent
            accelCurrent = sqrt((x * x + y * y + z * z).toDouble()).toFloat()
            val delta = accelCurrent - accelLast
            shake = shake * 0.9f + delta

            if (expectingShake && shake > 12) {  // Shake threshold
                expectingShake = false
                timer?.cancel()
                val maxTime = 3000L
                val bonus = (timeLeftMillis.toDouble() / maxTime * 10).toInt()
                score += bonus.coerceAtLeast(1)
                scoreText.text = "Score: $score"
                showNextAction()
            }
        }

        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(sensorListener,
            sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
            SensorManager.SENSOR_DELAY_UI)
    }

    override fun onPause() {
        sensorManager.unregisterListener(sensorListener)
        super.onPause()
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

        expectingShake = nextAction == "SHAKE IT"

        timer?.cancel()
        timer = object : CountDownTimer(3000, 50) {
            override fun onTick(millisUntilFinished: Long) {
                timeLeftMillis = millisUntilFinished
                timerProgress.progress = millisUntilFinished.toInt()

                val fraction = millisUntilFinished.toFloat() / 3000f

                val color = when {
                    fraction > 0.66f -> ContextCompat.getColor(this@MainActivity, R.color.timer_green)
                    fraction > 0.33f -> ContextCompat.getColor(this@MainActivity, R.color.timer_yellow)
                    else -> ContextCompat.getColor(this@MainActivity, R.color.timer_red)
                }

                timerProgress.progressTintList = ColorStateList.valueOf(color)
            }

            override fun onFinish() {
                if (actionButton.text.equals("DON'T TAP IT")){
                    score += 10
                    scoreText.text = "Score: $score"
                    showNextAction()
                }else {
                    endGame()
                }
            }
        }.start()

        actionButton.setOnClickListener {
            timer?.cancel()

            if (actionButton.text.equals("DON'T TAP IT") || expectingShake){
                endGame()
            } else {
                val maxTime = 3000L
                val bonus = (timeLeftMillis.toDouble() / maxTime * 10).toInt() // max 10 points
                score += bonus.coerceAtLeast(1) // ensure at least 1 point

                scoreText.text = "Score: $score"
                showNextAction()
            }
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
        timeLeftMillis = 0L
        expectingShake = false
        actionButton.visibility = View.INVISIBLE
        actionButton.setOnClickListener(null)
        Toast.makeText(this, "Game Over! Score: $score", Toast.LENGTH_LONG).show()
    }

}