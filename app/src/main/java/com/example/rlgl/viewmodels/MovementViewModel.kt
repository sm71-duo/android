package com.example.rlgl.viewmodels

import androidx.lifecycle.ViewModel
import kotlin.math.abs
import kotlin.math.floor

class MovementViewModel: ViewModel() {
    var xMovement: Double = 0.0
    var yMovement: Double = 0.0
    var zMovement: Double = 0.0


    fun setMovement(x: Double, y: Double, z: Double){
        xMovement = abs(floor(x* 100.0) / 100.0)
        yMovement = abs(floor(y* 100.0) / 100.0)
        zMovement = abs(floor(z* 100.0) / 100.0)
    }

    fun getTotalMovement(): Double {

        return floor((xMovement + yMovement + zMovement)*100)/100
    }

    fun calculateMovementDangerColor(): String {

        when(getTotalMovement()){
            in 0.0..3.0 -> return "#FFFFFF"
            in 3.0..4.0 -> return "#FFb2b2"
            in 4.0..5.0 -> return "#FF6666"
        }

        return "#FF0000"
    }
}