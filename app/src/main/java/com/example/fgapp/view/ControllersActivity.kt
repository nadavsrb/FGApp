package com.example.fgapp.view

import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.fgapp.R
import com.example.fgapp.databinding.ActivityControllersBinding
import com.example.fgapp.view_model.ControllersVM
import com.example.fgapp.view_model.ViewModel

class ControllersActivity : AppCompatActivity(), Joystick.JoystickListener {
    private val contrVM: ControllersVM = ViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityControllersBinding>(this,
            R.layout.activity_controllers)

        binding.contrller = contrVM
    }

    override fun onPause() {
        super.onPause()
        contrVM.disconnect()
    }

    override fun onJoystickMoved(xPercent: Float, yPercent: Float, source: Int) {
        contrVM.aileron = xPercent
        contrVM.elevator = yPercent
    }
}