package org.devio.hi.library.coroutine

import android.nfc.Tag
import android.util.Log
import kotlinx.coroutines.*

object CoroutineScene {
    private const val TAG:String = "CoroutineScene"
    fun startScene1() {
        GlobalScope.launch (Dispatchers.Main) {
            Log.e(TAG, "coroutine is running")
            val result1 = request1()
            val result2 = request2(result1)
            val result3 = request3(result2)
            
            updateUI(result3)
        }
        Log.e(TAG, "coroutine is launched")
    }

    /**
     * 启动一个线程，先执行request1, 完了以后，同时运行request2和request3
     */
    fun startScene2() {
        GlobalScope.launch (Dispatchers.Main) {
            Log.e(TAG, "coroutine is running")

            val result1 = request1();

            val deferred2 = GlobalScope.async { request2(result1)}
            val deferred3 = GlobalScope.async { request3(result1)}

            updateUI(deferred2.await(), deferred3.await())
        }
    }

    private fun updateUI(result2: String, result3: String) {
        Log.e(TAG, "updateUi work on ${Thread.currentThread().name}")
        Log.e(TAG, "paramter:$result3---$result2")
    }

    private fun updateUI(result3: String) {
        Log.e(TAG, "updateUi work on ${Thread.currentThread().name}")
        Log.e(TAG, "paramter:$result3")
    }

    suspend fun request1():String {
        delay(2*1000)
        Log.e(TAG, "request1 work on ${Thread.currentThread().name}")
        return "result from request1"
    }

    suspend fun request2(result1: String):String {
        delay(2*1000)
        Log.e(TAG, "request2 work on ${Thread.currentThread().name}")
        return "result from request2"
    }

    suspend fun request3(result2: String):String {
        delay(2*1000)
        Log.e(TAG, "request3 work on ${Thread.currentThread().name}")
        return "result from request3"
    }
}
