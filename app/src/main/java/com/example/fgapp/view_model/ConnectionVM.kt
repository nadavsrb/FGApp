package com.example.fgapp.view_model

import java.util.concurrent.Future

interface ConnectionVM {
    fun connect(ip: String, port: Int): Future<Boolean>?
}