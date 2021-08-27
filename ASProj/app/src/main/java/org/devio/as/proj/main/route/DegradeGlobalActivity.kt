package org.devio.`as`.proj.main.route

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Route
import org.devio.`as`.proj.common.ui.view.EmptyView
import org.devio.`as`.proj.main.R

/**
 * 全局的统一错误页
 */
@Route(path = "/degrade/global/activity")
class DegradeGlobalActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.layout_global_degrade)

        val emptyView = findViewById<EmptyView>(R.id.empty_view)
        emptyView.setIcon(R.string.if_unexpected1)
        emptyView.setTitle(getString(R.string.degrade_tips))
    }
}