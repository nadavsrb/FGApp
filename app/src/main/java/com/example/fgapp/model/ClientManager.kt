package com.example.fgapp.model

import java.io.PrintWriter
import java.net.InetSocketAddress
import java.net.Socket
import java.util.concurrent.Callable
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Future

class ClientManager : Model {
    private val connTimeout = 2000
    private var socket: Socket? = null
    private var out: PrintWriter? = null
    private var threadPool: ExecutorService? = null

    override fun connect(ip:String, port:Int): Future<Boolean>? {
        disconnect()
        threadPool = Executors.newFixedThreadPool(1)

        return threadPool?.submit(Callable{
                try {
                    val socket = Socket()
                    socket.connect(InetSocketAddress(ip, port), connTimeout)
                    out = PrintWriter(socket.getOutputStream(), true)
                } catch (tExp: java.net.SocketTimeoutException) {
                    return@Callable false
                }catch (ioExp: java.io.IOException) {
                    return@Callable false
                }

                return@Callable true
            })
    }

    override fun disconnect() {
        try {
            socket?.close()
        }catch (ioExp: java.io.IOException){}
        socket = null

        threadPool?.shutdown()
        threadPool = null
    }

    override fun changeVar(varName: String, value: Float) {
        if(socket == null || out == null) {
            return
        }

        threadPool?.execute{
            out?.print("set /controls/flight/$varName$value\r\n")
            out?.flush()
        }
    }

}