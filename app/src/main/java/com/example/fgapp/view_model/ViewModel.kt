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
    override var aileron: Float = 0.0F
        set(value) {
            model.changeVar("aileron", value)
        }
    override var rudder: Int = 100
        set(value) {
            model.changeVar("rudder", (value.toFloat() / 100) - 1)
        }
    override var throttle: Int = 0
        set(value) {
            model.changeVar("throttle", value.toFloat() / 100)
        }
    override var elevator: Float = 0.0F
        set(value) {
            model.changeVar("elevator", value)
        }

    override fun disconnect() {
        model.disconnect()
    }
}