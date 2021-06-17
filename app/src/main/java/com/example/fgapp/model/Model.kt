package com.example.fgapp.model

import java.util.concurrent.Future

interface Model {
    fun connect(ip:String, port:Int): Future<Boolean>?
    fun disconnect()
    fun changeVar(varName: String, value: Double)
}