package com.goosescout.vkupload.ui.welcome

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.goosescout.vkupload.ui.state.UserViewModel
import com.vk.api.sdk.auth.VKAuthenticationResult
import com.vk.api.sdk.auth.VKScope

@Composable
fun LoginButton(
    userViewModel: UserViewModel,
    onBeforeLaunch: () -> Unit = {},
    onSuccess: (VKAuthenticationResult) -> Unit = {},
    onFail: (VKAuthenticationResult) -> Unit = {}
) {
    val vkAuthLauncher = rememberLauncherForActivityResult(
        userViewModel.getLoginActivityResultContract()
    ) { result ->
        when (result) {
            is VKAuthenticationResult.Success -> onSuccess(result)
            is VKAuthenticationResult.Failed -> onFail(result)
        }
    }

    Button(
        onClick = {
            onBeforeLaunch()
            vkAuthLauncher.launch(listOf(VKScope.PHOTOS))
        },
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
        modifier = Modifier.wrapContentWidth().wrapContentHeight()
    ) {
        Text(
            text = "Войти через VK ID",
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.padding(start = 8.dp, end = 8.dp, top = 6.dp, bottom = 6.dp)
        )
    }
}