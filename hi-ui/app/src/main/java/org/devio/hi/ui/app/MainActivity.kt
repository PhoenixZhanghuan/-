package org.devio.hi.ui.app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import org.devio.hi.ui.app.refresh.HiRefreshDemoActivity
import org.devio.hi.ui.app.tab.HiTabBottomDemoActivity
import org.devio.hi.ui.app.tab.HiTabTopDemoActivity
import org.devio.hi.ui.tab.bottom.HiTabBottom
import org.devio.hi.ui.tab.bottom.HiTabBottomInfo

class MainActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    fun onClick(v: View?) {
        when (v!!.id) {
            R.id.tv_tab_bottom -> {
                startActivity(Intent(this, HiTabBottomDemoActivity::class.java))
            }
            R.id.tv_tab_top -> {
                startActivity(Intent(this, HiTabTopDemoActivity::class.java))
            }
            R.id.tv_hi_refresh -> {
                startActivity(Intent(this, HiRefreshDemoActivity::class.java))
            }
        }
    }
}