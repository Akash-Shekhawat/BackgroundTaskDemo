package com.example.backgroundtaskdemo.Services

import android.app.Service


import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import android.widget.Toast
import com.example.backgroundtaskdemo.R

class MusicService : Service() {

    private lateinit var mediaPlayer: MediaPlayer

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }



    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Toast.makeText(this, "Service started", Toast.LENGTH_SHORT).show()
        mediaPlayer = MediaPlayer.create(this, R.raw.astro)
        mediaPlayer.isLooping = false
        mediaPlayer.start()
        return START_STICKY

    }



    override fun onStart(intent: Intent?, startId: Int) {
        Toast.makeText(this, "Service started", Toast.LENGTH_SHORT).show()
        mediaPlayer.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        Toast.makeText(this, "service stopped", Toast.LENGTH_SHORT).show()
        mediaPlayer.stop()

    }

}