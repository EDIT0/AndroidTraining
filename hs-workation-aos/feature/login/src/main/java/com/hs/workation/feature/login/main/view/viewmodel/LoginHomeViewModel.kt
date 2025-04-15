package com.hs.workation.feature.login.main.view.viewmodel

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.hs.workation.core.base.viewmodel.BaseAndroidViewModel
import com.hs.workation.core.common.R
import com.hs.workation.core.common.constants.NetworkConstants
import com.hs.workation.core.model.base.RequestResult
import com.hs.workation.core.model.base.SideEffectEvent
import com.hs.workation.core.model.dto.IdAndPassword
import com.hs.workation.core.util.LogUtil
import com.hs.workation.core.util.NetworkManager
import com.hs.workation.domain.usecase.PostRequestLoginUseCase
import com.hs.workation.domain.usecase.PostRequestLogoutUseCase
import com.hs.workation.feature.login.main.event.LoginHomeViewModelEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginHomeViewModel @Inject constructor(
    app: Application,
    networkManager: NetworkManager,
    private val postRequestLoginUseCase: PostRequestLoginUseCase,
    private val postRequestLogoutUseCase: PostRequestLogoutUseCase
): BaseAndroidViewModel(app, networkManager) {

    private val scope = viewModelScope
    private var postRequestLoginUseCaseJob: Job? = null
    private var postRequestLogoutUseCaseJob: Job? = null

    private var accessToken: String? = null

    private val _sideEffectEvent = Channel<SideEffectEvent>()
    val sideEffectEvent = _sideEffectEvent.receiveAsFlow()

    fun handleViewModelEvent(loginHomeViewModelEvent: LoginHomeViewModelEvent) {
        when(loginHomeViewModelEvent) {
            is LoginHomeViewModelEvent.RequestLogin -> {
                requestLogin()
            }
            is LoginHomeViewModelEvent.RequestLogout -> {
                val token = accessToken?.let {
                    "bearer ${it}"
                }?:""
                if(token == "") {

                    return
                } else {
                    tmpLogout(token)
                }
            }
        }
    }

    private fun requestLogin() {
        postRequestLoginUseCaseJob?.cancel()
        postRequestLoginUseCaseJob = scope.launch {
            if(!networkManager.checkNetworkState()) {
                _sideEffectEvent.send(SideEffectEvent.NetworkError(app.getString(R.string.network_connection_check)))
                return@launch
            }

            postRequestLoginUseCase.invoke(IdAndPassword("01096355104", "password"))
                .onStart {
                }
                .onCompletion {
                }
                .filter {
                    val errorCode = it.code
                    val errorMessage = "${it.message}"

                    if(it is RequestResult.Error) {
                        when(errorCode) {
                            NetworkConstants.ERROR_BAD_REQUEST -> {
                                LogUtil.e_dev("${errorCode} ${errorMessage}")
                            }
                            NetworkConstants.ERROR_UNAUTHORIZED -> {
                                LogUtil.e_dev("${errorCode} ${errorMessage}")
                            }
                            NetworkConstants.ERROR_FORBIDDEN -> {
                                LogUtil.e_dev("${errorCode} ${errorMessage}")
                            }
                            NetworkConstants.ERROR_NOT_FOUND -> {
                                LogUtil.e_dev("${errorCode} ${errorMessage}")
                            }
                            NetworkConstants.ERROR_NOT_PROVIDE_API -> {
                                LogUtil.e_dev("${errorCode} ${errorMessage}")
                            }
                            NetworkConstants.ERROR_INTERNAL_SERVER -> {
                                LogUtil.e_dev("${errorCode} ${errorMessage}")
                            }
                        }
                    }

                    if(it is RequestResult.DataEmpty) {
                        LogUtil.e_dev("${it} ${errorMessage}")
                    }

                    return@filter it is RequestResult.Success
                }
                .catch {
                    LogUtil.e_dev("에러캐치: ${it}")
                }
                .collect {
                    LogUtil.d_dev("로그인 결과: ${it.resultData}")
                    accessToken = it.resultData?.access_token
                }
        }
    }

    // TODO [전종욱] : 임시 코드로 나중에 삭제 예정
    private fun tmpLogout(token: String) {
        postRequestLogoutUseCaseJob?.cancel()
        postRequestLogoutUseCaseJob = scope.launch {
            if(!networkManager.checkNetworkState()) {
                _sideEffectEvent.send(SideEffectEvent.NetworkError(app.getString(R.string.network_connection_check)))
                return@launch
            }

            postRequestLogoutUseCase.invoke(token = token)
                .onStart {
                }
                .onCompletion {
                }
                .filter {
                    val errorCode = it.code
                    val errorMessage = "${it.message}"

                    if(it is RequestResult.Error) {
                        when(errorCode) {
                            NetworkConstants.ERROR_BAD_REQUEST -> {
                                LogUtil.e_dev("${errorCode} ${errorMessage}")
                            }
                            NetworkConstants.ERROR_UNAUTHORIZED -> {
                                LogUtil.e_dev("${errorCode} ${errorMessage}")
                            }
                            NetworkConstants.ERROR_FORBIDDEN -> {
                                LogUtil.e_dev("${errorCode} ${errorMessage}")
                            }
                            NetworkConstants.ERROR_NOT_FOUND -> {
                                LogUtil.e_dev("${errorCode} ${errorMessage}")
                            }
                            NetworkConstants.ERROR_NOT_PROVIDE_API -> {
                                LogUtil.e_dev("${errorCode} ${errorMessage}")
                            }
                            NetworkConstants.ERROR_INTERNAL_SERVER -> {
                                LogUtil.e_dev("${errorCode} ${errorMessage}")
                            }
                        }
                    }

                    if(it is RequestResult.DataEmpty) {
                        LogUtil.e_dev("${it} ${errorMessage}")
                    }

                    return@filter it is RequestResult.Success
                }
                .catch {
                    LogUtil.e_dev("에러캐치: ${it}")
                }
                .collect {
                    LogUtil.d_dev("로그아웃 결과: ${it.resultData}")
                    accessToken = null
                }
        }
    }
}