package com.example.rlgl.viewmodels

import androidx.lifecycle.ViewModel

class MovementViewModel: ViewModel() {
    var xMovement: Float = 0F
    var yMovement: Float = 0F
    var zMovement: Float = 0F


    fun setMovement(x: Float, y: Float, z: Float){
        xMovement = x
        yMovement = y
        zMovement = z
    }

    fun getMovementX(): Float {
        return xMovement
    }
}