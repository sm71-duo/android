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

    fun calculateMovementDangerColor(): String {

        when(getTotalMovement()){
            0.0 -> return "#FFFFFF"
            1.0 -> return "#FFFFFF"
            2.0 -> return "#FFFFFF"
            3.0 -> return "#FFb2b2"
            4.0 -> return "#FF6666"
            5.0 -> return "#FF0000"
        }

        return "#FF0000"
    }
}