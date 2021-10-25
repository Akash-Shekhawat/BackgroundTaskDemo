package com.example.backgroundtaskdemo.Broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class BatteryStatusReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val batterylevel = intent?.getIntExtra("level",0)?: return
        if (batterylevel!=0){
            Toast.makeText(context, "Batter percentage $batterylevel%", Toast.LENGTH_SHORT).show()
        }
    }

}