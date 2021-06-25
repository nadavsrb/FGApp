package com.example.fgapp.view

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PixelFormat
import android.graphics.PorterDuff
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceHolder.Callback
import android.view.SurfaceView
import android.view.View
import kotlin.math.min
import kotlin.math.pow
import kotlin.math.sqrt


class Joystick: SurfaceView, Callback, View.OnTouchListener {
    private var centerX:Float = 0.0F
    private var centerY:Float = 0.0F
    private var baseRadius:Float = 0.0F
    private var hatRadius:Float = 0.0F
    private var joystickCallback: JoystickListener? = null
    private var RATIO:Int = 18

    interface JoystickListener {
        fun onJoystickMoved(xPercent: Float, yPercent: Float, source: Int)
    }

    constructor (c: Context): super(c){
        setZOrderOnTop(true)
        holder.setFormat(PixelFormat.TRANSLUCENT)
        holder.addCallback(this)
        setOnTouchListener(this)
        if (context is JoystickListener) {
            joystickCallback = context as JoystickListener
        }
    }
    constructor (c: Context, a: AttributeSet, style:Int): super(c, a, style){
        setZOrderOnTop(true)
        holder.setFormat(PixelFormat.TRANSLUCENT)
        holder.addCallback(this)
        setOnTouchListener(this)
        if (context is JoystickListener) {
            joystickCallback = context as JoystickListener
        }
    }
    constructor (c: Context, a: AttributeSet): super(c,a){
        setZOrderOnTop(true)
        holder.setFormat(PixelFormat.TRANSLUCENT)
        holder.addCallback(this)
        setOnTouchListener(this)
        if (context is JoystickListener) {
            joystickCallback = context as JoystickListener
        }
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

        hatRadius = min(width.toFloat(),  height.toFloat()) / 6
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

        val hypotenuse =
            sqrt((newX - centerX).pow(2.0F) + (newY - centerY).pow(2.0F))
        var sin = 0.0F
        var cos = 0.0F
        if(hypotenuse != 0.0F) {
            sin = (newY - centerY) / hypotenuse //sin = o/h
            cos = (newX - centerX) / hypotenuse //cos = a/h
        }
        for(i in (baseRadius / RATIO).toInt() downTo 1){
            colors.setARGB(255,
                (i * (200 * RATIO/baseRadius)).toInt(),
                (i * (200 * RATIO/baseRadius)).toInt(),
                (i * (200 * RATIO/baseRadius)).toInt())
            myCanvas.drawCircle(newX - cos * hypotenuse * (RATIO/baseRadius) * i, newY - sin * hypotenuse * (RATIO/baseRadius) * i, i * (hatRadius * RATIO / baseRadius), colors);
        }

        for(i in (hatRadius / (2 * RATIO)).toInt()..(hatRadius / RATIO).toInt()) {
            colors.setARGB(255,
                (i * (255 * RATIO/hatRadius)).toInt(),
                (i * (92 * RATIO/hatRadius)).toInt(),
                (i * (92 * RATIO/hatRadius)).toInt())

            myCanvas.drawCircle(newX, newY, hatRadius - i.toFloat() * RATIO / 1.1F, colors)
        }

        holder.unlockCanvasAndPost(myCanvas)

        joystickCallback?.onJoystickMoved(
            (newX - centerX) / baseRadius, (centerY - newY) / baseRadius, id)
    }

    override fun onTouch(view: View, event: MotionEvent): Boolean {
        //Checks if the touch is from in this view surface
        if(view == this){
            val displacement =
                sqrt((event.x - centerX).pow(2.0F) + (event.y - centerY).pow(2.0F))

            // Checks if the touch isn't the user lifting their finger off the touch screen.
            if(event.action != MotionEvent.ACTION_UP){
                if(displacement < baseRadius) {
                    drawJoystick(event.x, event.y)
                }else{
                    val ratio = baseRadius / displacement
                    val constrainedX = centerX + (event.x - centerX) * ratio
                    val constrainedY = centerY + (event.y - centerY) * ratio
                    drawJoystick(constrainedX, constrainedY)
                }
            }else{
                drawJoystick(centerX, centerY)
            }
        }

        return true
    }
}