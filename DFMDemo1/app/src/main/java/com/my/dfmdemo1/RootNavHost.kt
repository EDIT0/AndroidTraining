package com.my.dfmdemo1

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.my.dfmdemo1.core.navigation.CountryName
import com.my.dfmdemo1.core.navigation.CountryRepository
import com.my.dfmdemo1.core.navigation.ScreenName
import com.my.dfmdemo1.feature.intro.kr.IntroScreen
import com.my.dfmdemo1.feature.setting.common.SettingScreen

@Composable
fun RootNavHost(
    navHostController: NavHostController,
    startDestination: String = ScreenName.IntroScreen.name
) {
    NavHost(
        navController = navHostController,
        startDestination = startDestination,
        exitTransition = {
            ExitTransition.None
        },
        enterTransition = {
            EnterTransition.None
        }
    ) {
        composable(
            route = ScreenName.IntroScreen.name
        ) {
            when(CountryRepository.country) {
                CountryName.Ko.name -> {
                    IntroScreen(
                        navController = navHostController
                    )
                }
                CountryName.Common.name, CountryName.Ja.name, CountryName.Zh.name -> {
                    com.my.dfmdemo1.feature.intro.common.IntroScreen(
                        navController = navHostController
                    )
                }
            }
        }

        composable(
            route = ScreenName.HomeScreen.name
        ) {
            when(CountryRepository.country) {
                CountryName.Common.name -> {
                    com.my.dfmdemo1.feature.home.common.HomeScreen(
                        navController = navHostController
                    )
                }
                CountryName.Ko.name -> {
                    com.my.dfmdemo1.feature.home.kr.HomeScreen(
                        navController = navHostController
                    )
                }
                CountryName.Ja.name -> {
                    com.my.dfmdemo1.feature.home.jp.HomeScreen(
                        navController = navHostController
                    )
                }
                CountryName.Zh.name-> {
                    com.my.dfmdemo1.feature.home.cn.HomeScreen(
                        navController = navHostController
                    )
                }
            }
        }

        composable(
            route = ScreenName.SettingScreen.name
        ) {
            when(CountryRepository.country) {
                CountryName.Common.name, CountryName.Ko.name, CountryName.Ja.name, CountryName.Zh.name -> {
                    SettingScreen(
                        navController = navHostController
                    )
                }
            }
        }
    }
}