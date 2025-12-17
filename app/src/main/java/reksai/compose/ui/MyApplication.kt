package reksai.compose.ui

import android.app.Application
import reksai.compose.core.config.MyConfig

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        init()
    }

    fun init() {
        MyConfig.init(MyConfigHandle())
    }
}