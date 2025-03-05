package com.edit.widgetexample1

object Util {

    fun getRandomNumber(min: Int = 0, max: Int = 100): Int {
        return (min..max).random()
    }

}