package com.hs.workation.feature.permission.main.view.viewmodel

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.hs.workation.core.base.viewmodel.BaseAndroidViewModel
import com.hs.workation.core.util.NetworkManager
import com.hs.workation.core.util.PermissionManager
import com.hs.workation.feature.permission.main.event.PermissionCheckViewModelEvent
import com.hs.workation.feature.permission.main.event.PermissionStateUiErrorEvent
import com.hs.workation.feature.permission.main.event.PermissionStateUiEvent
import com.hs.workation.feature.permission.main.state.PermissionStateUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.runningFold
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PermissionCheckViewModel @Inject constructor(
    app: Application,
    networkManager: NetworkManager
): BaseAndroidViewModel(app, networkManager) {

    private val scope = viewModelScope

    private val _sideEffectEvent = Channel<SideEffectEvent>()
    val sideEffectEvent = _sideEffectEvent.receiveAsFlow()

    private val _permissionStateUiState = MutableStateFlow<PermissionStateUiEvent>(
        PermissionStateUiEvent.Idle)
    val permissionStateUiState: StateFlow<PermissionStateUiState> = _permissionStateUiState
        .runningFold(PermissionStateUiState()) { state, event ->
            when(event) {
                is PermissionStateUiEvent.Idle -> {
                    state
                }
                is PermissionStateUiEvent.UpdateState -> {
                    state.copy(isState = event.isState, isRetryAvailable = event.isRetryAvailable, rejectedPermissions = event.rejectedPermissions)
                }
            }
        }.stateIn(scope, SharingStarted.Eagerly, PermissionStateUiState())

    private val _permissionStateUiErrorEvent = Channel<PermissionStateUiErrorEvent>()
    val permissionStateUiErrorEvent = _permissionStateUiErrorEvent.receiveAsFlow()

    private lateinit var permissionManager: PermissionManager

    fun handleViewModelEvent(permissionCheckViewModelEvent: PermissionCheckViewModelEvent) {
        when (permissionCheckViewModelEvent) {
            is PermissionCheckViewModelEvent.InitPermissionManager -> {
                permissionManager = PermissionManager(
                    activity = permissionCheckViewModelEvent.activity,
                    result = { isRetryAvailable, rejectedPermissions ->
                        if(!isRetryAvailable) {
                            // 셋팅페이지
                            scope.launch {
                                _permissionStateUiState.emit(PermissionStateUiEvent.UpdateState(isState = false, isRetryAvailable = false, rejectedPermissions = rejectedPermissions))
                            }
                        } else if(isRetryAvailable && rejectedPermissions.isNotEmpty()) {
                            // 재요청
                            scope.launch {
                                _permissionStateUiState.emit(PermissionStateUiEvent.UpdateState(isState = false, isRetryAvailable = true, rejectedPermissions = rejectedPermissions))
                            }
                        } else {
                            // 통과
                            if(!permissionManager.isGrantedPermissions(arrayListOf(permissionManager.permissionOfBackgroundLocation))) {
                                permissionManager.requestPermissions(arrayListOf(permissionManager.permissionOfBackgroundLocation))
                            } else {
                                scope.launch {
                                    _permissionStateUiState.emit(PermissionStateUiEvent.UpdateState(isState = true, isRetryAvailable = false, rejectedPermissions = rejectedPermissions))
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

        _permissionStateUiErrorEvent.close()
        _sideEffectEvent.close()
    }

    enum class SideEffectEvent {
        NETWORK_ERROR
    }
}