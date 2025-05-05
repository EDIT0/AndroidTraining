package com.my.dfmdemo1.feature.setting.common

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.my.dfmdemo1.core.navigation.ActivityNavigator
import com.my.dfmdemo1.core.navigation.CountryName
import com.my.dfmdemo1.core.navigation.CountryRepository
import com.my.dfmdemo1.core.navigation.Language
import com.my.dfmdemo1.core.navigation.ScreenName
import com.my.dfmdemo1.core.util.PreferencesUtil
import javax.inject.Inject

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
        Text("Setting-Common")

        Button(
            onClick = {
                PreferencesUtil.putString(localContext, PreferencesUtil.KEY_COUNTRY, CountryName.Kr.name)
                CountryRepository.country = CountryName.Kr.name
                PreferencesUtil.putString(localContext, PreferencesUtil.KEY_LANGUAGE, Language.Kr.name)
                CountryRepository.language = Language.Kr.name
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
            Text("Set Kr")
        }

        Button(
            onClick = {
                PreferencesUtil.putString(localContext, PreferencesUtil.KEY_COUNTRY, CountryName.Jp.name)
                CountryRepository.country = CountryName.Jp.name
                PreferencesUtil.putString(localContext, PreferencesUtil.KEY_LANGUAGE, Language.Jp.name)
                CountryRepository.language = Language.Jp.name
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
            Text("Set Jp")
        }

        Button(
            onClick = {
                PreferencesUtil.putString(localContext, PreferencesUtil.KEY_COUNTRY, CountryName.Cn.name)
                CountryRepository.country = CountryName.Cn.name
                PreferencesUtil.putString(localContext, PreferencesUtil.KEY_LANGUAGE, Language.Cn.name)
                CountryRepository.language = Language.Cn.name
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
            Text("Set Cn")
        }

        Button(
            onClick = {
                PreferencesUtil.putString(localContext, PreferencesUtil.KEY_COUNTRY, CountryName.Common.name)
                CountryRepository.country = CountryName.Common.name
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
            Text("Set Common")
        }
    }
}
