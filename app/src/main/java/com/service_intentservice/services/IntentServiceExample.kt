package com.service_intentservice.services

import android.app.IntentService
import android.content.Intent
import android.util.Log
import java.lang.Exception

class IntentServiceExample : IntentService("intent_service_demo") {
    private val TAG = "IntentServiceExample"

    init {
        instance = this
        Log.d(instance.TAG, "Intent Service initialized")
    }

    companion object {
        private lateinit var instance: IntentServiceExample
        var isRunning = false

        fun stopService() {
            if (isRunning) {
                instance.stopSelf()
                Log.d(instance.TAG, "Intent Service stopped")
            }
            isRunning = false
        }
    }

    override fun onHandleIntent(intent: Intent?) {
        try {
            isRunning = true
//            while (isRunning) {
                intent?.let {
                    Log.d(TAG, "Job ${it.getIntExtra("jobNumber", -1)} is running ")
                    Thread.sleep(2000)
//                }
            }
        } catch (ex: Exception) {
            Thread.currentThread().interrupt()
        }
    }
}