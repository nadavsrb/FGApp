package com.example.fgapp.view

import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Patterns
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.example.fgapp.R
import com.example.fgapp.view_model.ConnectionVM
import com.example.fgapp.view_model.ViewModel

/**
 * This is the activity for connecting the flight gear.
 */
class MainActivity : AppCompatActivity() {
    private var connVM:ConnectionVM = ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()

        // updates the status text to be empty.
        findViewById<TextView>(R.id.errorLog).setText(R.string.empty_str)
    }

    /**
     * This func would happen when the connection button is pressed,
     * it would connect the flight gear, and if error occurred will
     * update the status text.
     */
    fun connectFG(view: View){
        // gets the ip and the port.
        val ip = findViewById<EditText>(R.id.ipField).text.toString()
        val portText = findViewById<EditText>(R.id.portField).text.toString()

        // gets the status text element
        val errorLog = findViewById<TextView>(R.id.errorLog)

        if(ip.isEmpty() || portText.isEmpty()){
            // if there is a missing field tells the user.
            errorLog.setTextColor(ContextCompat.getColor(view.context, R.color.red))
            errorLog.setText(R.string.error_missing_fields)
            return
        }

        if (!Patterns.IP_ADDRESS.matcher(ip).matches()) {
            // if there is a problem int the ip structure tells the user.
            errorLog.setTextColor(ContextCompat.getColor(view.context, R.color.red))
            errorLog.setText(R.string.error_ip)
            return
        }

        try {
            // casting the port to int
            val port = portText.toInt()

            // tells the user that we are trying to connect
            errorLog.setTextColor(ContextCompat.getColor(view.context, R.color.black))
            errorLog.setText(R.string.connecting)

            //connecting in a different runnable so message will get to the user.
            errorLog.post {
                // waiting for response if succeeded to connect.
                if (connVM.connect(ip, port)?.get() == true) {
                    // if succeeded go to the controllers activity.
                    startActivity(Intent(this, ControllersActivity::class.java))
                } else {
                    //else let the user know we had an error.
                    errorLog.setTextColor(ContextCompat.getColor(view.context, R.color.red))
                    errorLog.setText(R.string.error_failed_connecting)
                }
            }
        } catch(e: NumberFormatException) {
            // if the port isn't int tells the user.
            errorLog.setTextColor(ContextCompat.getColor(view.context, R.color.red))
            errorLog.setText(R.string.error_port)
        }
    }
}