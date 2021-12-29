package org.devio.hi.library.executor

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.annotation.IntRange
import java.util.concurrent.*
import java.util.concurrent.atomic.AtomicLong
import java.util.concurrent.locks.Condition
import java.util.concurrent.locks.ReentrantLock

/**
 * 支持按任务的优先级去执行，
 * 支持线程池暂停，恢复（批量文件下载，上传）
 * 异步结果主动回调主线程
 */
object HiExecutor {
    private const val TAG: String = "HiExecutor"
    private var isPaused: Boolean = false
    private var hiExecutor: ThreadPoolExecutor
    private var lock: ReentrantLock
    private var pauseCondition: Condition
    private val mainHandler = Handler(Looper.getMainLooper())
    init {
        lock = ReentrantLock()
        pauseCondition = lock.newCondition()
        val cpuCount = Runtime.getRuntime().availableProcessors()
        val corePoolSize = cpuCount + 1
        val maxPoolSize = cpuCount * 2 + 1
        val blockingQueue: PriorityBlockingQueue<out Runnable> = PriorityBlockingQueue()
        val keepAliveTime = 30L
        val unit = TimeUnit.SECONDS

        val seq = AtomicLong()
        val threadFactory = ThreadFactory {
            val thread = Thread(it)
            thread.name = "hi-executor-" + seq.getAndIncrement()
            return@ThreadFactory thread
        }

        hiExecutor = object: ThreadPoolExecutor(
            corePoolSize,
            maxPoolSize,
            keepAliveTime,
            unit,
            blockingQueue as BlockingQueue<Runnable>,
            threadFactory
        ) {
            override fun beforeExecute(t: Thread?, r: Runnable?) {
                if(!isPaused) {
                    lock.lock()
                    try {
                        pauseCondition.await()
                    } finally {
                        lock.unlock()
                    }
                }
            }

            override fun afterExecute(r: Runnable?, t: Throwable?) {
                Log.e(TAG, "已执行完的任务的优先级是：" + (r as PriorityRunnable).priority)
            }
        }
    }

    @JvmOverloads
    fun execute(@IntRange(from = 0,to = 10)priority: Int = 0, runnable: Runnable) {
        hiExecutor.execute(PriorityRunnable(priority, runnable))
    }

    @JvmOverloads
    fun execute(@IntRange(from = 0,to = 10)priority: Int = 0, runnable: Callable<*>) {
        hiExecutor.execute(PriorityRunnable(priority, runnable))
    }

    abstract class Callable<T> : Runnable {
        override fun run() {
            mainHandler.post {onPrepare()}
            val t = onBackground()
            mainHandler.post {onCompleted(t)}
        }

        open fun onPrepare(){
            //转菊花
        }
        abstract fun onBackground():T
        abstract fun onCompleted(t:T)
    }


    class PriorityRunnable(val priority: Int, val runnable: Runnable):Runnable, Comparable<PriorityRunnable> {
        override fun compareTo(other: PriorityRunnable): Int {
            return if(this.priority < other.priority) 1 else if(this.priority > other.priority) -1 else 0
        }

        override fun run() {
            runnable.run()
        }

    }

    @Synchronized
    fun pause() {
        isPaused = true
        Log.e(TAG, "hiexecutor is paused")
    }

    @Synchronized
    fun resume() {
        isPaused = false
        lock.lock()
        try {
            pauseCondition.signalAll()
        }finally {
            lock.unlock()
        }
        Log.e(TAG, "hiexecutor is resumed")
    }
}