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
    override var rudder: Float = 0.0F
        set(value) {
            model.changeVar("rudder", value)
        }
    override var throttle: Float = 0.0F
        set(value) {
            model.changeVar("throttle", value)
        }
    override var elevator: Float = 0.0F
        set(value) {
            model.changeVar("elevator", value)
        }

    override fun disconnect() {
        model.disconnect()
    }
}