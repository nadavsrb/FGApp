package com.example.fgapp.view_model

interface ControllersVM {
    var aileron: Float
    var rudder: Int
    var throttle: Int
    var elevator: Float
    fun disconnect()
}