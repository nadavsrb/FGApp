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
        errorLog.setTextColor(ContextCompat.getColor(view.context, R.color.red))

        if(ip.isEmpty() || portText.isEmpty()){
            errorLog.setText(R.string.error_missing_fields)
            return
        }

        if (!Patterns.IP_ADDRESS.matcher(ip).matches()) {
            errorLog.setText(R.string.error_ip)
            return
        }

        try {
            val port = portText.toInt()
            errorLog.setTextColor(ContextCompat.getColor(view.context, R.color.black))
            errorLog.setText(R.string.connecting)

            errorLog.post {
                if (connVM.connect(ip, port)?.get() == true) {
                    startActivity(Intent(this, ControllersActivity::class.java))
                } else {
                    errorLog.setTextColor(ContextCompat.getColor(view.context, R.color.red))
                    errorLog.setText(R.string.error_failed_connecting)
                }
            }
        } catch(e: NumberFormatException) {
            errorLog.setText(R.string.error_port)
        }
    }
}