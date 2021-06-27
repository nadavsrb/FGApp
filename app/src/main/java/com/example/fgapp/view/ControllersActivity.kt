package com.example.fgapp.view

import android.content.Intent
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.fgapp.R
import com.example.fgapp.databinding.ActivityControllersBinding
import com.example.fgapp.view_model.ControllersVM
import com.example.fgapp.view_model.ViewModel

/**
 * This activity is the controllers activity.
 * its also a type of Joystick.JoystickListener,
 * so onJoystickMoved she can update values.
 */
class ControllersActivity : AppCompatActivity(), Joystick.JoystickListener {
    private val contrVM: ControllersVM = ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // binding the ControllersVM (View Model) to xml.
        val binding = DataBindingUtil.setContentView<ActivityControllersBinding>(this,
            R.layout.activity_controllers)
        binding.contrller = contrVM
    }

    override fun onPause() {
        super.onPause()

        // disconnecting this activity.
        contrVM.disconnect()

        // return to the main activity.
        startActivity(Intent(this, MainActivity::class.java))
    }

    override fun onJoystickMoved(xPercent: Float, yPercent: Float, source: Int) {
        // updating aileron & elevator values.
        contrVM.aileron = xPercent
        contrVM.elevator = yPercent
    }
}