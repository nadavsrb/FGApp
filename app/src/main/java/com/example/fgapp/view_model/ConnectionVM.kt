package com.example.fgapp.view_model

interface ConnectionVM {
    fun connect(ip: String, port: Int): Boolean
}