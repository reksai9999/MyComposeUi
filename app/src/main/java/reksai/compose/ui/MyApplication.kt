package reksai.compose.ui

import android.app.Application
import reksai.compose.core.config.MyGlobalConfig

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        init()
    }

    fun init() {
        MyGlobalConfig.init(MyConfigHandle())
    }
}