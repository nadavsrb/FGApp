package com.example.fgapp.model

import java.util.concurrent.Future

/**
 * This is the interface of the model for the FGApp.
 */
interface Model {

    /**
     * Connecting to the flight gear.
     * @param ip String the ip of the flight gear.
     * @param port Int the port of the flight gear.
     * @return Future<Boolean>? returns a Future that represents
     * the status: true if succeeded, else false.
     */
    fun connect(ip:String, port:Int): Future<Boolean>?

    /**
     * Disconnects from the flight gear.
     */
    fun disconnect()

    /**
     * Sets a new value to the var by sending a message
     * to the flight gear.
     * @param varName String the vars name.
     * @param value Float the vars value.
     */
    fun changeVar(varName: String, value: Float)
}