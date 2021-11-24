package com.example.rlgl.activities

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.example.rlgl.R
import com.example.rlgl.databinding.ActivityMainBinding
import com.example.rlgl.viewmodels.GameViewModel
import com.example.rlgl.viewmodels.MovementViewModel
import com.example.rlgl.viewmodels.ShakeViewModel
import com.squareup.seismic.ShakeDetector

class MainActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var sensorManagerMovement: SensorManager
    private lateinit var binding: ActivityMainBinding
    private var mAcceleration: Sensor? = null
    private val movementViewModel: MovementViewModel by viewModels()
    private val gameViewModel: GameViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityMainBinding.inflate(layoutInflater)
        val view: View = binding.root
        setContentView(view)

        initializeMovementDetector()

    }

    override fun onResume() {
        super.onResume()
        mAcceleration?.also { acceleration ->
            sensorManagerMovement.registerListener(this, acceleration, 2000000)
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManagerMovement.unregisterListener(this)
    }


    override fun onAccuracyChanged(event: Sensor?, accuracy: Int) {

    }

    override fun onSensorChanged(event: SensorEvent?) {
//        if(gameViewModel.greenlight) return //For later
        val accelerationX = event!!.values[0].toDouble()
        val accelerationY = event!!.values[1].toDouble()
        val accelerationZ = event!!.values[2].toDouble()

        movementViewModel.setMovement(accelerationX, accelerationY, accelerationZ)

        binding.mainView.background = returnBackgroundGradient(movementViewModel.getTotalMovement())
    }

    private fun initializeMovementDetector() {
        sensorManagerMovement = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        mAcceleration = sensorManagerMovement.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION)
    }

    private fun returnBackgroundGradient(movementValue: Double): Drawable? {
         val gradientOne: Drawable? = ResourcesCompat.getDrawable(resources, R.drawable.gradient_one, null)
         val gradientTwo: Drawable? = ResourcesCompat.getDrawable(resources, R.drawable.gradient_two, null)
         val gradientThree: Drawable? = ResourcesCompat.getDrawable(resources, R.drawable.gradient_three, null)
         val emptyGradient: Drawable? = ResourcesCompat.getDrawable(resources, R.drawable.empty, null)

        when(movementValue){
            in 0.0..4.0 -> return emptyGradient
            in 4.0..5.0 -> return gradientOne
            in 5.0..7.0 -> return gradientTwo
        }
        return gradientThree
    }
}