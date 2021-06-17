package com.example.fgapp.model

interface Model {
    fun connect(ip:String, port:Int): Boolean
    fun disconnect()
    fun changeVar(varName: String, value: Double)
}