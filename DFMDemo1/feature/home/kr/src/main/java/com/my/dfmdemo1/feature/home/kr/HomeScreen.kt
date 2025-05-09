package com.my.dfmdemo1.feature.home.kr

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.my.dfmdemo1.core.navigation.ScreenName
import com.my.dfmdemo1.resource.ui.theme.lightBlue500

@Composable
fun HomeScreen(
    navController: NavController
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = lightBlue500())
    ) {
        Column {
            Text(
                text = "Home-UI(Kr)\nLanguage: ${LocalContext.current.getString(com.my.dfmdemo1.resource.R.string.welcome)}",
                style = TextStyle(fontSize = 30.sp)
            )

            Button(
                onClick = {
                    navController.navigate(route = ScreenName.SettingScreen.name)
                }
            ) {
                Text("Move to SettingScreen")
            }
        }
    }
}