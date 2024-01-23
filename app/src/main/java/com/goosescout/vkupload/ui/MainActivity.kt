package com.goosescout.vkupload.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import com.goosescout.vkupload.VKUploadApplication
import com.goosescout.vkupload.data.impl.UserRepositoryImpl
import com.goosescout.vkupload.ui.VKUploadApp
import com.goosescout.vkupload.ui.theme.VKUploadTheme
import com.vk.api.sdk.auth.VKScope

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val appContainer = (application as VKUploadApplication).container
        setContent {
            VKUploadTheme {
                VKUploadApp(appContainer)
            }
        }
    }
}