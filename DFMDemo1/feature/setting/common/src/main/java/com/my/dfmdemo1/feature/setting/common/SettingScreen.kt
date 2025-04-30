package com.my.dfmdemo1.feature.setting.common

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.my.dfmdemo1.core.navigation.CountryName
import com.my.dfmdemo1.core.navigation.CountryRepository
import com.my.dfmdemo1.core.navigation.ScreenName
import com.my.dfmdemo1.core.util.PreferencesUtil

@Composable
fun SettingScreen(
    navController: NavController
) {

    val localContext = LocalContext.current

    Column {
        Text("Setting-Common")

        Button(
            onClick = {
                PreferencesUtil.putString(localContext, PreferencesUtil.KEY_COUNTRY, CountryName.Kr.name)
                CountryRepository.country = CountryName.Kr.name
                navController.navigate(route = ScreenName.IntroScreen.name) {
                    popUpTo(navController.graph.startDestinationId) {
                        inclusive = true
                    }
                    launchSingleTop = true
                }
            }
        ) {
            Text("Set Kr")
        }

        Button(
            onClick = {
                PreferencesUtil.putString(localContext, PreferencesUtil.KEY_COUNTRY, CountryName.Jp.name)
                CountryRepository.country = CountryName.Jp.name
                navController.navigate(route = ScreenName.IntroScreen.name) {
                    popUpTo(navController.graph.startDestinationId) {
                        inclusive = true
                    }
                    launchSingleTop = true
                }
            }
        ) {
            Text("Set Jp")
        }

        Button(
            onClick = {
                PreferencesUtil.putString(localContext, PreferencesUtil.KEY_COUNTRY, CountryName.Cn.name)
                CountryRepository.country = CountryName.Cn.name
                navController.navigate(route = ScreenName.IntroScreen.name) {
                    popUpTo(navController.graph.startDestinationId) {
                        inclusive = true
                    }
                    launchSingleTop = true
                }
            }
        ) {
            Text("Set Cn")
        }

        Button(
            onClick = {
                PreferencesUtil.putString(localContext, PreferencesUtil.KEY_COUNTRY, CountryName.Common.name)
                CountryRepository.country = CountryName.Common.name
                navController.navigate(route = ScreenName.IntroScreen.name) {
                    popUpTo(navController.graph.startDestinationId) {
                        inclusive = true
                    }
                    launchSingleTop = true
                }
            }
        ) {
            Text("Set Common")
        }
    }
}