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

/**
 * This class represents a joystick.
 */
class Joystick: SurfaceView, Callback, View.OnTouchListener {
    private val RATIO:Int = 18

    private var centerX:Float = 0.0F
    private var centerY:Float = 0.0F
    private var baseRadius:Float = 0.0F
    private var hatRadius:Float = 0.0F
    private var joystickCallback: JoystickListener? = null

    /**
     * An inner interface for those who want to listen whe the
     * joystick was moved.
     */
    interface JoystickListener {
        /**
         * Func to do when joystick was moved
         * @param xPercent Float the location in x between [-1,1].
         * @param yPercent Float the location in y between [-1,1].
         * @param source Int the joystick source that changed (for multiple joysticks)
         */
        fun onJoystickMoved(xPercent: Float, yPercent: Float, source: Int)
    }

    /**
     * Constructor(1) for creating the joystick.
     * @param c Context the context object.
     * @constructor
     */
     constructor (c: Context): super(c){
        // set the joystick to be on top.
        setZOrderOnTop(true)

        // set background transparent.
        holder.setFormat(PixelFormat.TRANSPARENT)

        // initializing interfaces things.
        holder.addCallback(this)
        setOnTouchListener(this)

        // check if context should listen.
        if (context is JoystickListener) {
            joystickCallback = context as JoystickListener
        }
    }

    /**
     * Constructor(2) for creating the joystick.
     * @param c Context the context object.
     * @param a AttributeSet field for father.
     * @param style Int field for father.
     * @constructor
     */
    constructor (c: Context, a: AttributeSet, style:Int): super(c, a, style){
        // set the joystick to be on top.
        setZOrderOnTop(true)

        // set background transparent.
        holder.setFormat(PixelFormat.TRANSPARENT)

        // initializing interfaces things.
        holder.addCallback(this)
        setOnTouchListener(this)

        // check if context should listen.
        if (context is JoystickListener) {
            joystickCallback = context as JoystickListener
        }
    }

    /**
     * Constructor(3) for creating the joystick.
     * @param c Context the context object.
     * @param a AttributeSet field for father.
     * @constructor
     */
    constructor (c: Context, a: AttributeSet): super(c,a){
        // set the joystick to be on top.
        setZOrderOnTop(true)

        // set background transparent.
        holder.setFormat(PixelFormat.TRANSPARENT)

        // initializing interfaces things.
        holder.addCallback(this)
        setOnTouchListener(this)

        // check if context should listen.
        if (context is JoystickListener) {
            joystickCallback = context as JoystickListener
        }
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        // sets the joystick's dimensions.
        setupDimensions()

        // draws the joystick.
        drawJoystick(centerX, centerY)
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {}

    override fun surfaceDestroyed(holder: SurfaceHolder) {}

    /**
     * Sets the dimensions of the joystick.
     */
    private fun setupDimensions(){
        // sets the location of the joystick
        centerX = width.toFloat() / 2
        centerY = height.toFloat() / 2

        //sets the parts size of the joystick.
        baseRadius = min(width.toFloat(),  height.toFloat()) / 3
        hatRadius = min(width.toFloat(),  height.toFloat()) / 6
    }

    /**
     * Draws the joystick with his hat in (newX,newY)
     * @param newX Float the x location.
     * @param newY Float the y location.
     */
    private fun drawJoystick(newX:Float, newY:Float){
        //Validate that the view surface was created.
        if(!holder.surface.isValid){
            return
        }

        // initializing vars to draw by.
        val myCanvas = this.holder.lockCanvas()
        val colors = Paint()

        // clear the canvas from what is on it
        myCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)

        // draws the base of the joystick.
        colors.setARGB(255, 50, 50, 50)
        myCanvas.drawCircle(centerX, centerY, baseRadius, colors)

        // calculating the sin and the cos of the triangle that is created from the center,
        // the new location and the straight up line from the x axe.
        val hypotenuse = sqrt((newX - centerX).pow(2.0F) + (newY - centerY).pow(2.0F))
        var sin = 0.0F
        var cos = 0.0F
        if(hypotenuse != 0.0F) {
            sin = (newY - centerY) / hypotenuse //sin = o/h
            cos = (newX - centerX) / hypotenuse //cos = a/h
        }

        // draws the stick of the joystick with shading.
        for(i in (baseRadius / RATIO).toInt() downTo 1){
            colors.setARGB(255,
                (i * (200 * RATIO/baseRadius)).toInt(),
                (i * (200 * RATIO/baseRadius)).toInt(),
                (i * (200 * RATIO/baseRadius)).toInt())
            myCanvas.drawCircle(newX - cos * hypotenuse * (RATIO/baseRadius) * i, newY - sin * hypotenuse * (RATIO/baseRadius) * i, i * (hatRadius * RATIO / baseRadius), colors)
        }

        // draws the hat of the joystick with shading.
        for(i in (hatRadius / (2 * RATIO)).toInt()..(hatRadius / RATIO).toInt()) {
            colors.setARGB(255,
                (i * (255 * RATIO/hatRadius)).toInt(),
                (i * (92 * RATIO/hatRadius)).toInt(),
                (i * (92 * RATIO/hatRadius)).toInt())

            myCanvas.drawCircle(newX, newY, hatRadius - i.toFloat() * RATIO / 1.1F, colors)
        }

        // changing the canvas.
        holder.unlockCanvasAndPost(myCanvas)

        // let the listeners that the joystick position was changed.
        joystickCallback?.onJoystickMoved(
            (newX - centerX) / baseRadius, (centerY - newY) / baseRadius, id)
    }

    override fun onTouch(view: View, event: MotionEvent): Boolean {
        //Checks if the touch is from in this view surface
        if(view == this){
            // calculating the length of change from the center.
            val displacement =
                sqrt((event.x - centerX).pow(2.0F) + (event.y - centerY).pow(2.0F))

            // Checks if the touch isn't the user lifting their finger off the touch screen.
            if(event.action != MotionEvent.ACTION_UP){
                //checks the change of the hat is in the base.
                if(displacement < baseRadius) {
                    // draws the joystick.
                    drawJoystick(event.x, event.y)
                }else{
                    // if not sets the change so the hat is in the base.
                    val ratio = baseRadius / displacement
                    val constrainedX = centerX + (event.x - centerX) * ratio
                    val constrainedY = centerY + (event.y - centerY) * ratio

                    // draws the joystick.
                    drawJoystick(constrainedX, constrainedY)
                }
            }else{
                // draws the joystick.
                drawJoystick(centerX, centerY)
            }
        }

        // let the touch know we should keep listen.
        return true
    }
}