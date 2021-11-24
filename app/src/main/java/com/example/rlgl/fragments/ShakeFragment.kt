package com.example.rlgl.fragments

import android.content.Context
import android.graphics.Color
import android.hardware.SensorManager
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.example.rlgl.R
import com.example.rlgl.databinding.FragmentShakeBinding
import com.example.rlgl.viewmodels.GameViewModel
import com.example.rlgl.viewmodels.ShakeViewModel
import com.squareup.seismic.ShakeDetector

class ShakeFragment : Fragment(), ShakeDetector.Listener {

    companion object {
        fun newInstance() = ShakeFragment()
    }

    private lateinit var shakeViewModel: ShakeViewModel
    private lateinit var gameViewModel: GameViewModel

    private lateinit var binding: FragmentShakeBinding

    private var sensorManagerShaker: SensorManager? = null
    private var shakeDetector: ShakeDetector? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize shake detector. We need to retrieve the system service on the parent activity
        sensorManagerShaker = requireActivity().getSystemService(Context.SENSOR_SERVICE) as SensorManager
        shakeDetector = ShakeDetector(this)
        shakeDetector!!.start(sensorManagerShaker)

        // Do not use yet
        // gameViewModel.startGame()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentShakeBinding.inflate(inflater, container, false)

        // Initialize the view models
        shakeViewModel = ViewModelProvider(this).get(ShakeViewModel::class.java)
        gameViewModel = ViewModelProvider(this).get(GameViewModel::class.java)

        // Display correct text on startup
        val stepCounterText = "${shakeViewModel.currentAmountOfShakes} / ${shakeViewModel.totalShakes}"
        binding.stepCounter.text = stepCounterText
        return binding.root
    }

    override fun hearShake() {
        // Cancel shake update when shakes complete
        if(shakeViewModel.shakesCompleted) return

        shakeViewModel.updateShakesAmount()
        val stepCounterText = "${shakeViewModel.currentAmountOfShakes} / ${shakeViewModel.totalShakes}"
        binding.stepCounter.text = stepCounterText

        // Update fragment color when shakes complete
        if(shakeViewModel.shakesCompleted){
            binding.fragmentLayout.setBackgroundColor(getResources().getColor(R.color.green_900))
        }
    }
}