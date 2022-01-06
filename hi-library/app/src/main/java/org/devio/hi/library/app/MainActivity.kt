package org.devio.hi.library.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import org.devio.hi.library.coroutine.CoroutineScene3
import org.devio.hi.library.coroutine.CountDownLatchDemo
import org.devio.hi.library.coroutine.SemaphoreDemo

class MainActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onClick(v: View) {
        when(v.id) {
            R.id.tv_hilog -> {
                SemaphoreDemo.main()
            }
        }
    }
}
