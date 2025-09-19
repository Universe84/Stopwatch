package com.example.stopwatch

import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.widget.Button
import android.widget.Chronometer
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    companion object{
        val TAG = "MainActivity"

        val TIME_ELAPSED = "time"

        val STARTED = "is started"
    }

    private lateinit var startButton : Button
    private lateinit var resetButton : Button
    private lateinit var timerClock : Chronometer
    private var timeElapsed = 0;
    private var isStarted = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        wireWidgets()
        if(savedInstanceState != null){

            timeElapsed = savedInstanceState.getInt(TIME_ELAPSED)
            isStarted = savedInstanceState.getBoolean(STARTED)

            if(isStarted){
                timerClock.setBase(SystemClock.elapsedRealtime() - timeElapsed)
                timerClock.start()
                startButton.text = "Stop"
            }
            else{
                timerClock.base = SystemClock.elapsedRealtime() - timeElapsed

                startButton.text = "Start"
            }
        }
        Log.d("MainActivity", "onCreate: YOU CREATED")


        startButton.setOnClickListener {
            if(!isStarted){
                startButton.text = "Stop"
                timerClock.setBase(SystemClock.elapsedRealtime() - timeElapsed)
                timerClock.start()
                isStarted = true
            }
            else{
                startButton.text = "Start"

                timerClock.stop()
                timeElapsed =  SystemClock.elapsedRealtime().toInt() - timerClock.getBase().toInt()
                isStarted = false
            }
        }

        resetButton.setOnClickListener {

            timerClock.setBase(SystemClock.elapsedRealtime())
            timeElapsed = 0
            if(!isStarted){
                startButton.text = "Start"
            }
        }
    }

    private fun wireWidgets(){
        startButton = findViewById(R.id.button_main_stopStart)
        resetButton = findViewById(R.id.button_main_reset)
        timerClock = findViewById(R.id.chronometer_main_stopwatch)
    }


    override fun onStart() {
        super.onStart()
        Log.d("MainActivity", "onStart: YOU STARTED")
    }

    override fun onResume() {
        super.onResume()
        Log.d("MainActivity", "onResume: YOU RESUMED")
    }

    override fun onPause() {
        super.onPause()
        Log.d("MainActivity", "onPause: YOU PAUSED")
    }

    override fun onStop() {
        super.onStop()
        Log.d("MainActivity", "onStop: YOU STOPPED")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("MainActivity", "onRestart: YOU RESTARTED")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("MainActivity", "onDestroy: YOU DESTROYED")
    }

    //to maintain the state between orentation changes we use saved instance states
    override fun onSaveInstanceState(outState: Bundle) {

        timerClock.stop()

        outState.putBoolean(STARTED, isStarted)
        if(isStarted){
            timeElapsed = (SystemClock.elapsedRealtime() - timerClock.base).toInt()
        }
        outState.putInt(TIME_ELAPSED, timeElapsed)
        super.onSaveInstanceState(outState)

    }

}


