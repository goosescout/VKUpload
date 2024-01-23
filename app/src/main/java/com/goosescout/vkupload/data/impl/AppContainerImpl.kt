package com.goosescout.vkupload.data.impl

import android.content.Context
import com.goosescout.vkupload.data.AppContainer
import com.goosescout.vkupload.data.UserRepository

class AppContainerImpl(private val applicationContext: Context) : AppContainer {
    override val userRepository: UserRepository by lazy {
        UserRepositoryImpl()
    }
}