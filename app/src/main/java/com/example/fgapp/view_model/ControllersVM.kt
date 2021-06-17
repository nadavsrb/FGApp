package com.example.fgapp.view_model

interface ControllersVM {
    var isConnected: Boolean
    var aileron: Double
    var rudder: Double
    var throttle: Double
    var elevator: Double
    fun disconnect()

}