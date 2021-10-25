package com.example.backgroundtaskdemo

import android.util.Log
import java.lang.Exception

class Thread1: Thread() {

    val tag = "thread1"

    override fun run() {
        for(i in 1..4) {
            try {
                sleep(500)
            } catch (e: Exception) {
                println(e)
            }
            Log.i(tag, "value$i")
        }
    }


}