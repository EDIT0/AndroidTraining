package com.my.dfmdemo1.feature.intro.kr

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.my.dfmdemo1.core.navigation.ScreenName

@Composable
fun IntroScreen(
    navController: NavController
) {

    Column {
        Text("Intro-UI(Kr)\n${LocalContext.current.getString(com.my.dfmdemo1.resource.R.string.welcome)}")
        Button(
            onClick = {
                navController.navigate(route = ScreenName.HomeScreen.name)
            }
        ) {
            Text("Move to HomeScreen")
        }
    }
}
