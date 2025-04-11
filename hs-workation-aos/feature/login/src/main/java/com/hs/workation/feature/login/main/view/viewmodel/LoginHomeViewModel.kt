package com.hs.workation.feature.login.main.view.viewmodel

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.hs.workation.core.base.viewmodel.BaseAndroidViewModel
import com.hs.workation.core.common.constants.NetworkConstants
import com.hs.workation.core.model.base.RequestResult
import com.hs.workation.core.model.test.req.ReqLogin
import com.hs.workation.core.util.LogUtil
import com.hs.workation.core.util.NetworkManager
import com.hs.workation.domain.usecase.PostRequestLoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginHomeViewModel @Inject constructor(
    app: Application,
    networkManager: NetworkManager,
    private val postRequestLoginUseCase: PostRequestLoginUseCase
): BaseAndroidViewModel(app, networkManager) {

    private val scope = viewModelScope

    init {
        scope.launch {
            postRequestLoginUseCase.invoke(ReqLogin("01096355104", "password"))
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
                }
        }
    }

}