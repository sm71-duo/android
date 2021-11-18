package com.example.rlgl.activities

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
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
import androidx.core.view.isVisible
import com.example.rlgl.databinding.ActivityMainBinding
import com.example.rlgl.viewmodels.MovementViewModel
import com.example.rlgl.viewmodels.ShakeViewModel
import com.squareup.seismic.ShakeDetector

class MainActivity : AppCompatActivity(), ShakeDetector.Listener, SensorEventListener {

    private var sensorManagerShaker: SensorManager? = null
    private lateinit var sensorManagerMovement: SensorManager
    private var shakeDetector: ShakeDetector? = null
    private lateinit var binding: ActivityMainBinding

    private var mAcceleration: Sensor? = null

    private val shakeViewModel: ShakeViewModel by viewModels()
    private val movementViewModel: MovementViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view: View = binding.root
        setContentView(view)

        initializeShaker()
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
        val accelerationX = event!!.values[0].toDouble()
        val accelerationY = event!!.values[1].toDouble()
        val accelerationZ = event!!.values[2].toDouble()

        movementViewModel.setMovement(accelerationX, accelerationY, accelerationZ)
        binding.totalMovement.text = movementViewModel.getTotalMovement().toString()

        binding.movementDanger.setTextColor(Color.parseColor(movementViewModel.calculateMovementDangerColor()))
    }


    override fun hearShake() {
        shakeViewModel.updateShakesAmount()
        binding.stepCounter.text = shakeViewModel.currentAmountOfShakes.toString()
    }

    private fun initializeShaker() {
        sensorManagerShaker = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        shakeDetector = ShakeDetector(this);
        shakeDetector!!.start(sensorManagerShaker)

        if(ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACTIVITY_RECOGNITION) == PackageManager.PERMISSION_DENIED){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(arrayOf(Manifest.permission.ACTIVITY_RECOGNITION), 1)
            };
        }
    }

    private fun initializeMovementDetector() {
        sensorManagerMovement = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        mAcceleration = sensorManagerMovement.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION)
    }
}