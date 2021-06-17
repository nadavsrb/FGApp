package com.example.fgapp.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.example.fgapp.R
import com.example.fgapp.view_model.ConnectionVM
import com.example.fgapp.view_model.ViewModel

class MainActivity : AppCompatActivity() {
    private var connVM:ConnectionVM = ViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun connectFG(view: View){
        val ip = findViewById<EditText>(R.id.ipField).text.toString()
        val portText = findViewById<EditText>(R.id.portField).text.toString()

        val errorLog = findViewById<TextView>(R.id.errorLog)
        errorLog.setTextColor(resources.getColor(R.color.red))

        if(ip.isEmpty() || portText.isEmpty()){
            errorLog.text = "Error: Missing Fields!!!"
            return
        }

        if (!Patterns.IP_ADDRESS.matcher(ip).matches()) {
            errorLog.text = "Error: This Isn't An IP!!!"
            return
        }

        try {
            val port = portText.toInt()
            errorLog.setTextColor(resources.getColor(R.color.black))
            errorLog.text = "Trying To Connect..."

            errorLog.post {
                if (connVM.connect(ip, port)?.get() == false) {
                    errorLog.setTextColor(resources.getColor(R.color.red))
                    errorLog.text = "Error: Unable To Connect FG!!!"
                }
            }
        } catch(e: NumberFormatException) {
            errorLog.text = "Error: Port Should Be Integer!!!"
        }
    }
}