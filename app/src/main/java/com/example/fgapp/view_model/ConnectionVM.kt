package com.example.fgapp.view_model

import java.util.concurrent.Future

/**
 * This interface is for view model that is used to connect the flight gear.
 */
interface ConnectionVM {

    /**
     * Connecting to the flight gear.
     * @param ip String the ip of the flight gear.
     * @param port Int the port of the flight gear.
     * @return Future<Boolean>? returns a Future that represents
     * the status: true if succeeded, else false.
     */
    fun connect(ip: String, port: Int): Future<Boolean>?
}