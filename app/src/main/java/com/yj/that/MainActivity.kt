package com.yj.that

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import com.yj.that.util.Utils
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(newBase)
        Utils.extractAssets(this,"that.apk")
    }
    private val conn by lazy {
        object :ServiceConnection{
            override fun onServiceDisconnected(name: ComponentName?) {

            }

            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            }
        }
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
        bt_bind_service.setOnClickListener {
            bindService(Intent(this, ProxyBindService::class.java),conn,Context.BIND_AUTO_CREATE)
        }
        bt_unBind_service.setOnClickListener {
           unbindService(conn)
        }
    }
}
