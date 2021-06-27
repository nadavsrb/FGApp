package com.example.fgapp.view_model
import com.example.fgapp.model.Model
import com.example.fgapp.model.ClientManager
import java.util.concurrent.Future

/**
 * An implementation of the View Model in the FGApp
 */
object ViewModel : ConnectionVM, ControllersVM {
    private val model: Model = ClientManager()

    override fun connect(ip: String, port: Int): Future<Boolean>? {
        // using the model to connect the flight gear.
        return  model.connect(ip, port)
    }

    override var aileron: Float = 0.0F
        set(value) {
            // setting the aileron val in the flight gear.
            model.changeVar("aileron", value)
        }

    override var rudder: Int = ControllersVM.FIXED_INT
        set(value) {
            // setting the rudder val in the flight gear.
            model.changeVar("rudder", (value.toFloat() / ControllersVM.FIXED_INT) - 1)
        }


    override var throttle: Int = 0
        set(value) {
            // setting the throttle val in the flight gear.
            model.changeVar("throttle", value.toFloat() / ControllersVM.FIXED_INT)
        }

    override var elevator: Float = 0.0F
        set(value) {
            // setting the elevator val in the flight gear.
            model.changeVar("elevator", value)
        }

    override fun disconnect() {
        // using the model to disconnect from the flight gear.
        model.disconnect()
    }
}