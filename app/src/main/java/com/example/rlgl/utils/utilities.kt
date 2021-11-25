package com.example.rlgl.utils

import java.util.*

class utilities {
    companion object {
        private val rnd = Random()

        fun randLong(min: Long, max: Long): Long {
            return (min + ((rnd.nextDouble() * (max - min))) * 1).toLong()
        }
    }
}