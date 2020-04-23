package com.servicetest

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), CallBack {
    override fun call(index: Int) {
        Log.e("abc", "This is index value : $index")
    }

    var mService: MyService? = null
    var isBind = false
    var mService2 = MyService()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val intent = Intent(this, MyService::class.java)
        val conn = object : ServiceConnection {
            override fun onServiceDisconnected(name: ComponentName?) {
                Log.e("abc", "-- Activity 中 onServiceDisconnected --")
            }

            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                mService = (service as MyService.MyBinder).getService()
                mService!!.setListener(this@MainActivity)
                mService!!.setListener(object :CallBack{
                    override fun call(index: Int) {
                        Log.e("abc", "This is index value : $index")
                    }
                })
                Log.e("abc", "-- Activity 中 onServiceConnected --")
            }
        }
//        bindService(intent, conn, 0)
        startService.setOnClickListener {
            startService(intent)
        }
        stopService.setOnClickListener {
            stopService(intent)
        }
        bindService.setOnClickListener {
            isBind = bindService(intent, conn, Context.BIND_AUTO_CREATE)
        }
        unBindService.setOnClickListener {
            if (isBind) {
                isBind = false
                unbindService(conn)
            }
        }
        testLog.setOnClickListener {
            mService!!.testLog()
            mService2.testLog()
        }
    }
}
