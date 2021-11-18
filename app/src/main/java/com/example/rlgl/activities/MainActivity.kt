package com.example.rlgl.activities

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.example.rlgl.databinding.ActivityMainBinding
import com.example.rlgl.viewmodels.MovementViewModel
import com.example.rlgl.viewmodels.ShakeViewModel
import com.squareup.seismic.ShakeDetector

class MainActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var sensorManagerMovement: SensorManager
    private lateinit var binding: ActivityMainBinding
    private var mAcceleration: Sensor? = null
    private val movementViewModel: MovementViewModel by viewModels()

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
            sensorManagerMovement.registerListener(this, acceleration, 1000000)
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManagerMovement.unregisterListener(this)
    }


    override fun onAccuracyChanged(event: Sensor?, accuracy: Int) {

    }

    override fun onSensorChanged(event: SensorEvent?) {
        val accelerationX = event!!.values[0].toDouble()
        val accelerationY = event!!.values[1].toDouble()
        val accelerationZ = event!!.values[2].toDouble()

        movementViewModel.setMovement(accelerationX, accelerationY, accelerationZ)
        binding.totalMovement.text = movementViewModel.getTotalMovement().toString()
        binding.xMovement.text = movementViewModel.xMovement.toString()
        binding.yMovement.text = movementViewModel.yMovement.toString()
        binding.zMovement.text = movementViewModel.zMovement.toString()
    }

    private fun initializeMovementDetector() {
        sensorManagerMovement = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        mAcceleration = sensorManagerMovement.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION)
    }
}