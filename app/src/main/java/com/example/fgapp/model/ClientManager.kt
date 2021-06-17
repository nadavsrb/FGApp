package com.example.fgapp.model

import java.io.PrintWriter
import java.net.Socket
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class ClientManager : Model {
    private var socket: Socket? = null
    private var out: PrintWriter? = null
    private var threadPool: ExecutorService? = null

    override fun connect(ip:String, port:Int): Boolean{
        disconnect()

        try {
            socket = Socket(ip, port)
            out = PrintWriter(socket!!.getOutputStream(), true)
            threadPool = Executors.newFixedThreadPool(1)
        }catch (ioExp: java.io.IOException){
            return false
        }

        return true
    }

    override fun disconnect() {
        try {
            socket?.close()
        }catch (ioExp: java.io.IOException){}
        socket = null

        threadPool?.shutdown()
        threadPool = null
    }

    override fun changeVar(varName: String, value: Double) {
        if(socket == null || out == null) {
            return
        }

        threadPool?.execute{
            out?.print("set /controls/flight/$varName$value\r\n")
            out?.flush()
        }
    }

}