package com.example.fgapp.view

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceHolder.*
import android.view.SurfaceView
import android.view.View
import kotlin.math.min

class Joystick: SurfaceView, Callback, View.OnTouchListener {
    private var centerX:Float = 0.0F
    private var centerY:Float = 0.0F
    private var baseRadius:Float = 0.0F
    private var hatRadius:Float = 0.0F

    constructor (c: Context): super(c){
        holder.addCallback(this)
        setOnTouchListener(this);
    }
    constructor (c: Context, a: AttributeSet, style:Int): super(c, a, style){
        holder.addCallback(this)
        setOnTouchListener(this);
    }
    constructor (c: Context, a: AttributeSet): super(c,a){
        holder.addCallback(this)
        setOnTouchListener(this);
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        setupDimensions()
        drawJoystick(centerX, centerY)
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
    }

    private fun setupDimensions(){
        centerX = width.toFloat() / 2

        centerY = height.toFloat() / 2

        baseRadius = min(width.toFloat(),  height.toFloat()) / 3

        hatRadius = min(width.toFloat(),  height.toFloat()) / 7
    }

    private fun drawJoystick(newX:Float, newY:Float){
        //Validate that the view surface was created.
        if(!holder.surface.isValid){
            return
        }

        val myCanvas = this.holder.lockCanvas()
        val colors = Paint()

        // clear the canvas from what is on it
        myCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)

        colors.setARGB(255, 50, 50, 50)
        myCanvas.drawCircle(centerX, centerY, baseRadius, colors)

        colors.setARGB(255, 150, 150, 150)
        myCanvas.drawCircle(newX, newY, hatRadius, colors)

        holder.unlockCanvasAndPost(myCanvas)
    }

    override fun onTouch(view: View, event: MotionEvent): Boolean {
        //Checks if the touch is from in this view surface
        if(view == this){
            // Checks if the touch is the user lifting their finger off the touch screen.
            if(event.action == MotionEvent.ACTION_UP){
                drawJoystick(centerX, centerY)
            } else{
                drawJoystick(event.x, event.y)
            }
        }

        return true
    }
}