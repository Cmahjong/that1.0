package com.yj.that

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.yj.that.util.Utils
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(newBase)
        Utils.extractAssets(this,"that.apk")
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bt_start_act.setOnClickListener {
            startActivity(Intent(this,ProxyActivity::class.java).apply {
                putExtra(ProxyActivity.EXTRA_LAUNCHER_CLASS_NAME,"com.yj.that_plugin.MainActivity")
            })
        }
        bt_start_service.setOnClickListener {
            startService(Intent(this, ProxyService::class.java))
        }
        bt_stop_service.setOnClickListener {
            stopService(Intent(this, ProxyService::class.java))
        }
    }
}
