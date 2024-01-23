package com.goosescout.vkupload.data

import androidx.activity.result.contract.ActivityResultContract
import com.vk.api.sdk.auth.VKAuthenticationResult
import com.vk.api.sdk.auth.VKScope

interface UserRepository {
    fun getLoginActivityResultContract(): ActivityResultContract<Collection<VKScope>, VKAuthenticationResult>
    fun logout()
    fun isLoggedIn(): Boolean
    suspend fun getNames(): List<String?>
}