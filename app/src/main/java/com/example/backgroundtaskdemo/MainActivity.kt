package com.example.backgroundtaskdemo

import android.content.*
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.backgroundtaskdemo.Broadcast.BatteryStatusReceiver
import com.example.backgroundtaskdemo.Services.BoundService
import com.example.backgroundtaskdemo.Services.MusicService
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    companion object {
        private const val PHONE_PERMISSION_CODE = 101
    }

    lateinit var batteryStatusReceiver: BatteryStatusReceiver
    var myService: BoundService? = null
    var isBound = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Broadcast Receiver
        btnReceiver.setOnClickListener {
            receiverDemoFunction()
            checkPermission(
                android.Manifest.permission.READ_PHONE_STATE,
                PHONE_PERMISSION_CODE
            )
        }

        val myThread1 = Thread1()
        val myThread2 = Thread1()
        val myThread3 = Thread1()



        myThread1.start()


        try {
            myThread1.join()
        } catch (e: Exception) {
            println(e)
        }

        myThread2.start()
        myThread3.start()



        service()
        bindLocalService()

        btnLocalService.setOnClickListener {
            showTime()
        }

    }



    private fun bindLocalService() {
        val intent = Intent(this, BoundService::class.java)
        bindService(intent, myConnection, Context.BIND_AUTO_CREATE)
    }

    private fun showTime() {
        val currentTime = myService?.getCurrentTime()
        Toast.makeText(this, "Current time is: ${currentTime.toString()}", Toast.LENGTH_SHORT)
            .show()
    }


    private val myConnection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            val binder = service as BoundService.MyLocalBinder
            myService = binder.getService()
            isBound = true
        }

        override fun onServiceDisconnected(name: ComponentName) {
            isBound = false
        }
    }


    private fun service() {
        val serviceIntent = Intent(this, MusicService::class.java)
        btnServiceStart.setOnClickListener {
            startService(serviceIntent)
        }
        btnServiceStop.setOnClickListener {
            stopService(serviceIntent)
        }
    }



    private fun receiverDemoFunction() {
        batteryStatusReceiver = BatteryStatusReceiver()
        registerReceiver(batteryStatusReceiver, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
    }


    override fun onStop() {
        super.onStop()
        unregisterReceiver(batteryStatusReceiver)
        unbindService(myConnection)

    }








    private fun checkPermission(permission: String, requestCode: Int) {
        if (ContextCompat.checkSelfPermission(this@MainActivity, permission) == PackageManager.PERMISSION_DENIED) {

            // Requesting the permission
            ActivityCompat.requestPermissions(this@MainActivity, arrayOf(permission), requestCode)
        } else {
            Toast.makeText(this@MainActivity, "Permission already granted", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>,
                                            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PHONE_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this@MainActivity, "PHONE Permission Granted", Toast.LENGTH_SHORT)
                    .show()
            } else {
                val intent = Intent(
                    ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.fromParts("package", packageName, null)
                )
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)

            }
        }

    }
}