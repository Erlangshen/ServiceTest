package com.servicetest

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.telecom.Call
import android.util.Log
import java.lang.Exception

class MyService: Service() {
    private var callBack:CallBack ?= null
    fun setListener(callBack: CallBack){
        this.callBack = callBack
    }
    var isGo = true
    var counter = 0
    override fun onBind(intent: Intent?): IBinder? {
        Log.e("abc", "-- onBind --")
        return MyBinder()
    }

    override fun onCreate() {
        super.onCreate()
        Log.e("abc", "-- onCreate --")
        Thread(Runnable {
            while (isGo) {
                try {
                    Thread.sleep(400)
                    Log.e("abc", "current index -->${counter}")
                    if (counter >= 100) {
                        callBack!!.call(counter)
                        break
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                counter += 3
            }
        }).start()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.e("abc", "-- onStartCommand --")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        isGo = false
        Log.e("abc", "-- onDestroy --")
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.e("abc", "-- onUnbind --")
        return super.onUnbind(intent)
    }

    fun testLog() {
        Log.e("abc", "-- testLog --")
    }

    inner class MyBinder : Binder() {
        fun getService(): MyService {
            return this@MyService
        }
    }
}