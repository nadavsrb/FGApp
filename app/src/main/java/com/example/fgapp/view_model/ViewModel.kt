package com.example.fgapp.view_model

object ViewModel : ConnectionVM, ControllersVM {
    val model = Model()
    override fun connect(ip: String, port: Int): Boolean {
        isConnected = model.connect(ip, port)
        return isConnected
    }

    override var isConnected: Boolean = false
    override var aileron: Double = 0.0
        set(value) {
            model.changeVar("aileron", value)
        }
    override var rudder: Double = 0.0
        set(value) {
            model.changeVar("rudder", value)
        }
    override var throttle: Double = 0.0
        set(value) {
            model.changeVar("throttle", value)
        }
    override var elevator: Double = 0.0
        set(value) {
            model.changeVar("elevator", value)
        }

    override fun disconnect() {
        model.disconnect()
    }

}