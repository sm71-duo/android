package com.example.rlgl

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
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.rlgl.databinding.ActivityMainBinding
import kotlinx.coroutines.delay
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity(), SensorEventListener {

    private var running = false
    private var sensorManager: SensorManager? = null
    private lateinit var binding: ActivityMainBinding
    private var count = 0
    private var yeet = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view: View = binding.root
        setContentView(view)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        if(ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACTIVITY_RECOGNITION) == PackageManager.PERMISSION_DENIED){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(arrayOf(Manifest.permission.ACTIVITY_RECOGNITION), 1)
            };
        }
    }

    override fun onResume() {
        super.onResume()
        running = true
        val stepsSensor = sensorManager?.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        if (stepsSensor == null) {
            Toast.makeText(this, "No Step Counter Sensor present on this phone!", Toast.LENGTH_SHORT).show()
        } else {
            sensorManager?.registerListener(this, stepsSensor, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    override fun onSensorChanged(event: SensorEvent?) {
        println(event!!.values[0])
        if (running) {
            val totalSteps = event!!.values[0];
            if (totalSteps > 5 && !yeet) {
                count++
                yeet = true
            }
            if(totalSteps < 1 && yeet) {
                yeet=false
            }
//            binding.stepCounter.text = totalSteps.toString()
            binding.stepCounter.text = count.toString()
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
    }
}