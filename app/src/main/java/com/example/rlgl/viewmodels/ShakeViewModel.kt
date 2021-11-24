package com.example.rlgl.viewmodels

import androidx.lifecycle.ViewModel

class ShakeViewModel : ViewModel() {
    var currentAmountOfShakes : Int = 0
    var totalShakes : Int = 10
    var shakesCompleted : Boolean = false

    private fun setComplete() {
        shakesCompleted = true
    }

    fun updateShakesAmount() {
        currentAmountOfShakes++
        if(currentAmountOfShakes == totalShakes) {
            setComplete()
        }
    }


}