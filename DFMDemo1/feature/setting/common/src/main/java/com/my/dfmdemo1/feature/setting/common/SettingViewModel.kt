package com.my.dfmdemo1.feature.setting.common

import androidx.lifecycle.ViewModel
import com.my.dfmdemo1.core.navigation.ActivityNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(): ViewModel(){

    @Inject
    lateinit var activityNavigator: ActivityNavigator

}