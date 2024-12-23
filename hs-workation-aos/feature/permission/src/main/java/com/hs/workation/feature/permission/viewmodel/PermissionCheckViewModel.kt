package com.hs.workation.feature.permission.viewmodel

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.hs.workation.core.base.viewmodel.BaseAndroidViewModel
import com.hs.workation.core.util.LogUtil
import com.hs.workation.core.util.NetworkManager
import com.hs.workation.core.util.PermissionManager
import com.hs.workation.feature.permission.event.PermissionCheckViewModelEvent
import com.hs.workation.feature.permission.event.PermissionStateUiErrorEvent
import com.hs.workation.feature.permission.event.PermissionStateUiEvent
import com.hs.workation.feature.permission.state.PermissionStateUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.runningFold
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PermissionCheckViewModel @Inject constructor(
    app: Application,
    networkManager: NetworkManager
): BaseAndroidViewModel(app, networkManager) {

    private val _sideEffectEvent = MutableSharedFlow<SideEffectEvent>()
    val sideEffectEvent = _sideEffectEvent.asSharedFlow()

    private val _permissionStateUiState = Channel<PermissionStateUiEvent>()
    val permissionStateUiState: SharedFlow<PermissionStateUiState> = _permissionStateUiState.receiveAsFlow()
        .runningFold(PermissionStateUiState()) { state, event ->
            when(event) {
                is PermissionStateUiEvent.UpdateState -> {
                    state.copy(isState = event.isState, isRetryAvailable = event.isRetryAvailable, rejectedPermissions = event.rejectedPermissions)
                }
            }
        }.shareIn(viewModelScope, SharingStarted.Eagerly, 0)

    private val _permissionStateUiErrorEvent = Channel<PermissionStateUiErrorEvent>()
    val permissionStateUiErrorEvent = _permissionStateUiErrorEvent.receiveAsFlow()

    private lateinit var permissionManager: PermissionManager

    fun handleViewModelEvent(permissionCheckViewModelEvent: PermissionCheckViewModelEvent) {
        when (permissionCheckViewModelEvent) {
            is PermissionCheckViewModelEvent.InitPermissionManager -> {
                permissionManager = PermissionManager(
                    activity = permissionCheckViewModelEvent.activity,
                    result = { isRetryAvailable, rejectedPermissions ->
                        LogUtil.d_dev(isRetryAvailable.toString() + "/" + rejectedPermissions.toString())

                        if(!isRetryAvailable) {
                            // 셋팅페이지
                            viewModelScope.launch {
                                _permissionStateUiState.send(PermissionStateUiEvent.UpdateState(isState = false, isRetryAvailable = false, rejectedPermissions = rejectedPermissions))
                            }
                        } else if(isRetryAvailable && rejectedPermissions.isNotEmpty()) {
                            // 재요청
                            viewModelScope.launch {
                                _permissionStateUiState.send(PermissionStateUiEvent.UpdateState(isState = false, isRetryAvailable = true, rejectedPermissions = rejectedPermissions))
                            }
                        } else {
                            // 통과
                            if(!permissionManager.isGrantedPermissions(arrayListOf(permissionManager.permissionOfBackgroundLocation))) {
                                permissionManager.requestPermissions(arrayListOf(permissionManager.permissionOfBackgroundLocation))
                            } else {
                                viewModelScope.launch {
                                    _permissionStateUiState.send(PermissionStateUiEvent.UpdateState(isState = true, isRetryAvailable = false, rejectedPermissions = rejectedPermissions))
                                }
                            }
                        }
                    }
                )
            }
            is PermissionCheckViewModelEvent.RequestPermission -> {
                requestEssentialPermissions()
            }
            is PermissionCheckViewModelEvent.NavigateToPermissionSettingPage -> {
                permissionManager.navigateToPermissionSettingPage()
            }
        }
    }

    private fun requestEssentialPermissions() {
        permissionManager.requestPermissions(permissionManager.getEssentialPermissions())
    }

    override fun onCleared() {
        super.onCleared()

        _permissionStateUiState.close()
        _permissionStateUiErrorEvent.close()
    }

    enum class SideEffectEvent {
        NETWORK_ERROR
    }
}