package com.my.dfmdemo1.feature.intro.common

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.my.dfmdemo1.core.navigation.ScreenName

@Composable
fun IntroScreen(
    navController: NavController
) {

    Column {
        Text("Intro-Common")
        Button(
            onClick = {
                navController.navigate(route = ScreenName.HomeScreen.name)
            }
        ) {
            Text("Move to HomeScreen")
        }
    }
}