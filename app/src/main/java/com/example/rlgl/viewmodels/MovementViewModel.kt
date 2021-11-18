package com.example.rlgl.viewmodels

import androidx.lifecycle.ViewModel
import kotlin.math.abs
import kotlin.math.floor

class MovementViewModel: ViewModel() {
    var xMovement: Double = 0.0
    var yMovement: Double = 0.0
    var zMovement: Double = 0.0


    fun setMovement(x: Double, y: Double, z: Double){
        xMovement = abs(floor(x))
        yMovement = abs(floor(y))
        zMovement = abs(floor(z))
    }

    fun getTotalMovement(): Double {
        return xMovement + yMovement + zMovement
    }
}