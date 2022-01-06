package org.devio.hi.library.coroutine;

import android.content.res.AssetManager
import android.util.Log
import kotlinx.coroutines.suspendCancellableCoroutine
import java.io.BufferedReader
import java.io.InputStreamReader

/**
 * 演示以异步的方式， 读取assets目录下的文件，并且适配协程的写法，让他真正的挂起函数
 *
 * 方便调用方 直接以同步的形式拿到返回值
 */
object CoroutineScene3 {
    suspend fun parseAssetsFile(assetManager: AssetManager, fileName: String): String {
        return suspendCancellableCoroutine { continuation ->
            Thread {
                val inputStream = assetManager.open(fileName)
                val br = BufferedReader(InputStreamReader(inputStream))
                var line: String?
                val stringBuilder = StringBuilder()

                do {
                    line = br.readLine()
                    if (line != null) stringBuilder.append(line) else break
                } while (true)

                inputStream.close()
                br.close()

                Thread.sleep(2000)
                Log.e("coroutine", "parseassetsfile completed")
                continuation.resumeWith(Result.success(stringBuilder.toString()))
            }.start()
        }
    }
}