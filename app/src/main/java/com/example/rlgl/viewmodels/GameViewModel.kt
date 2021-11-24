package com.example.rlgl.viewmodels

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.ViewModel
import java.util.*

class GameViewModel : ViewModel() {
    var greenlight: Boolean = true
    var isGameRunning: Boolean = false
    var interval: Long = 3000

    fun startGame() {
        isGameRunning = true
        startInterval()
    }

    private fun startInterval() {
        val rn = Random()

        while(isGameRunning) {
            Handler(Looper.getMainLooper()).postDelayed({
                greenlight = !greenlight
                interval = (rn.nextInt(10000 + 1000) + 1000).toLong()
            }, interval)
        }

        isGameRunning = false
    }
}