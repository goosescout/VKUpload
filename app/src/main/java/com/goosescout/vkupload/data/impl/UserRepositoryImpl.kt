package com.goosescout.vkupload.data.impl

import androidx.activity.result.contract.ActivityResultContract
import com.goosescout.vkupload.data.UserRepository
import com.vk.api.sdk.VK
import com.vk.api.sdk.VKApiCallback
import com.vk.api.sdk.auth.VKAuthenticationResult
import com.vk.api.sdk.auth.VKScope
import com.vk.sdk.api.users.UsersService
import com.vk.sdk.api.users.dto.UsersUserFullDto
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class UserRepositoryImpl : UserRepository {
    override fun getLoginActivityResultContract(): ActivityResultContract<Collection<VKScope>, VKAuthenticationResult> {
        return VK.getVKAuthActivityResultContract()
    }

    override fun logout() {
        VK.logout()
    }

    override fun isLoggedIn(): Boolean {
        return VK.isLoggedIn()
    }

    override suspend fun getNames(): List<String?> {
        return suspendCancellableCoroutine { continuation ->
            VK.execute(UsersService().usersGet(), object : VKApiCallback<List<UsersUserFullDto>> {
                override fun success(result: List<UsersUserFullDto>) {
                    if (result.isNotEmpty()) {
                        val currentUser = result[0]
                        val names = listOf(currentUser.firstName, currentUser.lastName)
                        continuation.resume(names)
                    } else {
                        continuation.resume(emptyList())
                    }
                }

                override fun fail(error: Exception) {
                    continuation.resumeWithException(error)
                }
            })
        }
    }
}