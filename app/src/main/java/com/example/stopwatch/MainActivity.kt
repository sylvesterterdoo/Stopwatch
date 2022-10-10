package com.example.stopwatch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.widget.Button
import android.widget.Chronometer
import com.example.stopwatch.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    lateinit var stopwatch: Chronometer
    var running  = false
    var offset: Long = 0

    val OFFSET_KEY = "offset"
    val RUNNING_KEY = "funning"
    val BASE_KEY = "base"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

//        stopwatch = findViewById<Chronometer>(R.id.stopwatch)

        if (savedInstanceState != null) {
            running = savedInstanceState.getBoolean(RUNNING_KEY)
            offset = savedInstanceState.getLong(OFFSET_KEY)
            if (running) {
                binding.stopwatch.base = savedInstanceState.getLong(BASE_KEY)
                binding.stopwatch.start()
            } else {
                setBaseTime()
            }
        }
        activitySetup()
    }

    private fun activitySetup() {

//        val startButton = findViewById<Button>(R.id.start_button)
        binding.startButton.setOnClickListener {
            if (!running) {
                setBaseTime()
                binding.stopwatch.start()
                running = true
            }
        }

//        val pauseButton = findViewById<Button>(R.id.pause_button)
        binding.pauseButton.setOnClickListener {
            if (running) {
                saveOffset()
                binding.stopwatch.stop()
                running = false
            }
        }

//        val resetButton = findViewById<Button>(R.id.reset_button)
        binding.resetButton.setOnClickListener {
            offset = 0
            setBaseTime()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putBoolean(RUNNING_KEY, running)
        outState.putLong(OFFSET_KEY, offset)
        outState.putLong(BASE_KEY, binding.stopwatch.base)
        super.onSaveInstanceState(outState)
    }

    override fun onStop() {
        super.onStop()
        if (running) {
            saveOffset()
            binding.stopwatch.stop()
        }
    }

    override fun onRestart() {
        super.onRestart()
        if (running) {
            setBaseTime()
            binding.stopwatch.start()
            offset = 0
        }
    }

    fun saveOffset() {
        offset = SystemClock.elapsedRealtime() - binding.stopwatch.base
    }

    fun setBaseTime() {
        binding.stopwatch.base = SystemClock.elapsedRealtime() - offset
    }
}