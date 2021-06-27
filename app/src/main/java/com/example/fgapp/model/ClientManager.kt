package com.example.fgapp.model

import java.io.PrintWriter
import java.net.InetSocketAddress
import java.net.Socket
import java.util.concurrent.Callable
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Future

/**
 * An implementation of the Model in the FGApp
 */
class ClientManager : Model {
    private val connTimeout = 2000
    private var socket: Socket? = null
    private var out: PrintWriter? = null
    private var threadPool: ExecutorService? = null

    override fun connect(ip:String, port:Int): Future<Boolean>? {
        // first make sure we are disconnecting from any connection.
        disconnect()

        // creating a thread pool withe 1 thread to handle io.
        threadPool = Executors.newFixedThreadPool(1)

        return threadPool?.submit(Callable{
                try {
                    // creating the connection to the flight gear.
                    socket = Socket()
                    socket!!.connect(InetSocketAddress(ip, port), connTimeout)
                    out = PrintWriter(socket!!.getOutputStream(), true)

                // if any error occurred returns false.
                } catch (tExp: java.net.SocketTimeoutException) {
                    return@Callable false
                }catch (ioExp: java.io.IOException) {
                    return@Callable false
                }catch (ioExp: NullPointerException) {
                    return@Callable false
                }

                // if we got here succeeded to connect.
                return@Callable true
            })
    }

    override fun disconnect() {
        // closing the socket if exists.
        try {
            socket?.close()
        }catch (ioExp: java.io.IOException){}
        socket = null

        // close the thread pool if exists.
        threadPool?.shutdown()
        threadPool = null

        // close the out stream if exists.
        out?.close()
        out = null
    }

    override fun changeVar(varName: String, value: Float) {
        // checking connection is set.
        if(socket == null || out == null) {
            return
        }

        // sending the set var message.
        threadPool?.execute{
            // gets the command for setting the var
            var startString = "set /controls/flight/"
            if(varName == "throttle"){
                startString = "set /controls/engines/current-engine/"
            }

            // sending the var
            out?.print("$startString$varName $value\r\n")
            out?.flush()
        }
    }
}