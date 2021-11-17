package com.example.rlgl.activities

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.SensorManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.example.rlgl.databinding.ActivityMainBinding
import com.example.rlgl.viewmodels.ShakeViewModel
import com.squareup.seismic.ShakeDetector

class MainActivity : AppCompatActivity(), ShakeDetector.Listener {

    private var sensorManager: SensorManager? = null
    private var shakeDetector: ShakeDetector? = null
    private lateinit var binding: ActivityMainBinding

    private  val shakeViewModel: ShakeViewModel = ShakeViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view: View = binding.root
        setContentView(view)

        initializeShaker()
    }

    override fun hearShake() {
        shakeViewModel.updateShakesAmount()
        binding.stepCounter.text = shakeViewModel.currentAmountOfShakes.toString()
    }

    private fun initializeShaker() {
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        shakeDetector = ShakeDetector(this);
        shakeDetector!!.start(sensorManager)

        if(ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACTIVITY_RECOGNITION) == PackageManager.PERMISSION_DENIED){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(arrayOf(Manifest.permission.ACTIVITY_RECOGNITION), 1)
            };
        }
    }
}