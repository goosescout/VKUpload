package com.goosescout.vkupload

import android.app.Application
import com.goosescout.vkupload.data.AppContainer
import com.goosescout.vkupload.data.impl.AppContainerImpl

class VKUploadApplication : Application() {

    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppContainerImpl(this)
    }
}