package com.example.fgapp.view_model

/**
 * This interface is for a view model that is used for setting vars
 * values in the flight gear and for allow to disconnect the flight gear.
 */
interface ControllersVM {
    companion object{
        const val FIXED_INT:Int = 100
    }


    /**
     * Represents the aileron it's value should be [-1,1]
     */
    var aileron: Float

    /**
     * Represents the rudder it's value should be [-1,1]
     * For implementations it's an int between [0,2 * FIXED_INT]
     * and when setting the val we would devide by FIXED_INT and decrease val by 1.
     */
    var rudder: Int

    /**
     * Represents the rudder it's value should be [0,1]
     * For implementations it's an int between [0,FIXED_INT]
     * and when setting the val we would devide by FIXED_INT.
     */
    var throttle: Int

    /**
     * Represents the elevator it's value should be [-1,1]
     */
    var elevator: Float

    /**
     * Disconnects from the flight gear.
     */
    fun disconnect()
}