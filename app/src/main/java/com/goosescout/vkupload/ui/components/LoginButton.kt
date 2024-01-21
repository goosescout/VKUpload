package com.goosescout.vkupload.ui.components

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKAuthenticationResult
import com.vk.api.sdk.auth.VKScope

@Composable
fun LoginButton(
    onSuccess: (VKAuthenticationResult) -> Unit,
    onFail: (VKAuthenticationResult) -> Unit
) {
    val vkAuthLauncher = rememberLauncherForActivityResult(
        VK.getVKAuthActivityResultContract()
    ) { result ->
        when (result) {
            is VKAuthenticationResult.Success -> onSuccess(result)
            is VKAuthenticationResult.Failed -> onFail(result)
        }
    }

    Button(
        onClick = { vkAuthLauncher.launch(listOf(VKScope.PHOTOS)) },
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
    ) {
        Text(
            text = "Войти через VK ID",
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.padding(start = 8.dp, end = 8.dp, top = 6.dp, bottom = 6.dp)
        )
    }
}