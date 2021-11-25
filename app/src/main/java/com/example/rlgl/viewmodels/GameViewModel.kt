package com.example.rlgl.viewmodels

import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rlgl.utils.utilities


class GameViewModel : ViewModel() {

    // The red light / green light indicator
    private val _greenLight = MutableLiveData<Boolean>()
    val greenLight: LiveData<Boolean>
        get() = _greenLight

    // Event which triggers the end of the game
    private val _eventGameFinished = MutableLiveData<Boolean>()
    val eventGameFinished: LiveData<Boolean>
        get() = _eventGameFinished

    // Countdown time
    private val _currentTime = MutableLiveData<Long>()
    val currentTime: LiveData<Long>
        get() = _currentTime

    // Game state
    private val _gameRunning = MutableLiveData<Boolean>()
    val gameRunning: LiveData<Boolean>
        get() = _gameRunning

    private lateinit var timer: CountDownTimer

    private var countdown = 3000L

    init {
        _greenLight.value = true
        _eventGameFinished.value = false
    }

    fun onGameFinish() {
        _eventGameFinished.value = true
        _gameRunning.value = false
    }

    fun startGame() {
        _gameRunning.value = true
        startTimer()
    }

    private fun startTimer() {
        // Creates a timer which triggers the end of the game when it finishes
        timer = object : CountDownTimer(countdown, 1000L) {

            override fun onTick(millisUntilFinished: Long) {
                _currentTime.value = millisUntilFinished/1000L
            }

            override fun onFinish() {
                changeLight()
            }
        }.start()
    }

    fun changeLight() {
        _greenLight.value = !_greenLight.value!!
        countdown = utilities.randLong(MIN, MAX)
        Log.i("Countdown", countdown.toString())
        timer.cancel()
        startTimer()
    }

    override fun onCleared() {
        super.onCleared()
        // Cancel the timer
        timer.cancel()
    }

    companion object {
        // Time when the game is over
        private const val DONE = 0L

        private const val MIN = 1000L

        private const val MAX = 5000L
    }
}