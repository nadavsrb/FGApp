package com.example.fgapp.view_model

interface ControllersVM {
    var isConnected: Boolean
    var aileron: Float
    var rudder: Int
    var throttle: Int
    var elevator: Float
    fun disconnect()

}