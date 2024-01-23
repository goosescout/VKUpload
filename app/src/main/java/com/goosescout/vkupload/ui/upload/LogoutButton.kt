package com.goosescout.vkupload.ui.upload

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKAuthenticationResult
import com.vk.api.sdk.auth.VKScope

@Composable
fun LogoutButton(
    onLogout: () -> Unit = {}
) {
    var isDialogShowing by remember { mutableStateOf(false) }

    Button(
        onClick = { isDialogShowing = true },
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.errorContainer),
        modifier = Modifier.wrapContentWidth().wrapContentHeight()
    ) {
        Text(
            text = "Выйти",
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.padding(start = 8.dp, end = 8.dp, top = 6.dp, bottom = 6.dp)
        )
    }

    if (isDialogShowing) {
        AlertDialog(
            onDismissRequest = { isDialogShowing = false },
            title = { Text("Выход") },
            text = { Text("Вы уверены, что хотите выйти?") },
            confirmButton = {
                Button(onClick = {
                    VK.logout()
                    onLogout()
                    isDialogShowing = false
                }) {
                    Text("Подтвердить")
                }
            },
            dismissButton = {
                Button(onClick = { isDialogShowing = false }) {
                    Text("Отмена")
                }
            }
        )
    }
}