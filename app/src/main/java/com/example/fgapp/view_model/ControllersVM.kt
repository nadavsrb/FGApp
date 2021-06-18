package com.example.fgapp.view_model

interface ControllersVM {
    var isConnected: Boolean
    var aileron: Float
    var rudder: Float
    var throttle: Float
    var elevator: Float
    fun disconnect()

}