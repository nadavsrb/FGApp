package com.example.fgapp.view_model
import com.example.fgapp.model.Model
import com.example.fgapp.model.ClientManager
import java.util.concurrent.Future

object ViewModel : ConnectionVM, ControllersVM {
    private val model: Model = ClientManager()
    override fun connect(ip: String, port: Int): Future<Boolean>? {
        return  model.connect(ip, port)
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