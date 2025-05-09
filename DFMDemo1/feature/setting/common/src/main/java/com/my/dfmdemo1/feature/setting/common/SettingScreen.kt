package com.my.dfmdemo1.feature.setting.common

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.my.dfmdemo1.core.navigation.CountryName
import com.my.dfmdemo1.core.navigation.CountryRepository
import com.my.dfmdemo1.core.navigation.Language
import com.my.dfmdemo1.core.util.PreferencesUtil

@Composable
fun SettingScreen(
    navController: NavController,
    settingViewModel: SettingViewModel = hiltViewModel()
) {

    val localContext = LocalContext.current

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        // 결과 처리
    }

    Column {
        Text("Setting-UI(Common)\n${localContext.getString(com.my.dfmdemo1.resource.R.string.welcome)}\nLanguage: ${localContext.getString(com.my.dfmdemo1.resource.R.string.app_name)}")

        Button(
            onClick = {
                PreferencesUtil.putString(localContext, PreferencesUtil.KEY_COUNTRY, CountryName.Ko.name)
                CountryRepository.country = CountryName.Ko.name
//                navController.navigate(route = ScreenName.IntroScreen.name) {
//                    popUpTo(navController.graph.startDestinationId) {
//                        inclusive = true
//                    }
//                    launchSingleTop = true
//                }
                settingViewModel.activityNavigator.navigateActivityToMainActivity(
                    context = localContext,
                    launcher = launcher,
                    options = null,
                    dataBundle = null
                )
            }
        ) {
            Text("Set UI Kr")
        }

        Button(
            onClick = {
                PreferencesUtil.putString(localContext, PreferencesUtil.KEY_COUNTRY, CountryName.Ja.name)
                CountryRepository.country = CountryName.Ja.name
//                navController.navigate(route = ScreenName.IntroScreen.name) {
//                    popUpTo(navController.graph.startDestinationId) {
//                        inclusive = true
//                    }
//                    launchSingleTop = true
//                }
                settingViewModel.activityNavigator.navigateActivityToMainActivity(
                    context = localContext,
                    launcher = launcher,
                    options = null,
                    dataBundle = null
                )
            }
        ) {
            Text("Set UI Jp")
        }

        Button(
            onClick = {
                PreferencesUtil.putString(localContext, PreferencesUtil.KEY_COUNTRY, CountryName.Zh.name)
                CountryRepository.country = CountryName.Zh.name
//                navController.navigate(route = ScreenName.IntroScreen.name) {
//                    popUpTo(navController.graph.startDestinationId) {
//                        inclusive = true
//                    }
//                    launchSingleTop = true
//                }
                settingViewModel.activityNavigator.navigateActivityToMainActivity(
                    context = localContext,
                    launcher = launcher,
                    options = null,
                    dataBundle = null
                )
            }
        ) {
            Text("Set UI Cn")
        }

        Button(
            onClick = {
                PreferencesUtil.putString(localContext, PreferencesUtil.KEY_COUNTRY, CountryName.Common.name)
                CountryRepository.country = CountryName.Common.name
//                navController.navigate(route = ScreenName.IntroScreen.name) {
//                    popUpTo(navController.graph.startDestinationId) {
//                        inclusive = true
//                    }
//                    launchSingleTop = true
//                }
                settingViewModel.activityNavigator.navigateActivityToMainActivity(
                    context = localContext,
                    launcher = launcher,
                    options = null,
                    dataBundle = null
                )
            }
        ) {
            Text("Set UI Common")
        }

        Button(
            onClick = {
                PreferencesUtil.putString(localContext, PreferencesUtil.KEY_LANGUAGE, Language.Ko.name)
                CountryRepository.language = Language.Ko.name
//                navController.navigate(route = ScreenName.IntroScreen.name) {
//                    popUpTo(navController.graph.startDestinationId) {
//                        inclusive = true
//                    }
//                    launchSingleTop = true
//                }
                settingViewModel.activityNavigator.navigateActivityToMainActivity(
                    context = localContext,
                    launcher = launcher,
                    options = null,
                    dataBundle = null
                )
            }
        ) {
            Text("Set language Kr")
        }

        Button(
            onClick = {
                PreferencesUtil.putString(localContext, PreferencesUtil.KEY_LANGUAGE, Language.Ja.name)
                CountryRepository.language = Language.Ja.name
//                navController.navigate(route = ScreenName.IntroScreen.name) {
//                    popUpTo(navController.graph.startDestinationId) {
//                        inclusive = true
//                    }
//                    launchSingleTop = true
//                }
                settingViewModel.activityNavigator.navigateActivityToMainActivity(
                    context = localContext,
                    launcher = launcher,
                    options = null,
                    dataBundle = null
                )
            }
        ) {
            Text("Set language Jp")
        }

        Button(
            onClick = {
                PreferencesUtil.putString(localContext, PreferencesUtil.KEY_LANGUAGE, Language.Zh.name)
                CountryRepository.language = Language.Zh.name
//                navController.navigate(route = ScreenName.IntroScreen.name) {
//                    popUpTo(navController.graph.startDestinationId) {
//                        inclusive = true
//                    }
//                    launchSingleTop = true
//                }
                settingViewModel.activityNavigator.navigateActivityToMainActivity(
                    context = localContext,
                    launcher = launcher,
                    options = null,
                    dataBundle = null
                )
            }
        ) {
            Text("Set language Cn")
        }

        Button(
            onClick = {
                PreferencesUtil.putString(localContext, PreferencesUtil.KEY_LANGUAGE, Language.Common.name)
                CountryRepository.language = Language.Common.name
//                navController.navigate(route = ScreenName.IntroScreen.name) {
//                    popUpTo(navController.graph.startDestinationId) {
//                        inclusive = true
//                    }
//                    launchSingleTop = true
//                }
                settingViewModel.activityNavigator.navigateActivityToMainActivity(
                    context = localContext,
                    launcher = launcher,
                    options = null,
                    dataBundle = null
                )
            }
        ) {
            Text("Set language Common")
        }
    }
}
