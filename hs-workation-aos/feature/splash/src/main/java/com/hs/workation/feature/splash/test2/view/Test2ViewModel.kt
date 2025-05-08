package com.hs.workation.feature.splash.test2.view

import android.app.Application
import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.hs.workation.core.base.viewmodel.BaseAndroidViewModel
import com.hs.workation.core.common.constants.NetworkConstants
import com.hs.workation.core.model.base.RequestResult
import com.hs.workation.core.model.dto.req.ReqLogin
import com.hs.workation.core.model.dto.req.ReqResignation
import com.hs.workation.core.model.dto.req.ReqSignUp
import com.hs.workation.core.util.CameraGalleryUtil
import com.hs.workation.core.util.JwtUtil
import com.hs.workation.core.util.LogUtil
import com.hs.workation.core.util.NetworkManager
import com.hs.workation.domain.usecase.PostRequestLoginUseCase
import com.hs.workation.domain.usecase.PostRequestLogoutUseCase
import com.hs.workation.domain.usecase.PostRequestResignationUseCase
import com.hs.workation.domain.usecase.PostRequestSignUpUseCase
import com.hs.workation.feature.splash.test2.event.Test2ViewModelEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class Test2ViewModel @Inject constructor(
    app: Application,
    networkManager: NetworkManager,
    private val cameraGalleryUtil: CameraGalleryUtil,
    private val postRequestLoginUseCase: PostRequestLoginUseCase,
    private val postRequestLogoutUseCase: PostRequestLogoutUseCase,
    private val postRequestSignUpUseCase: PostRequestSignUpUseCase,
    private val postRequestResignationUseCase: PostRequestResignationUseCase
): BaseAndroidViewModel(app, networkManager) {

    private val scope = viewModelScope
    private var scopeJob: Job? = null

    private var accessToken: String? = null
    private var memberId: String? = null

    private var imageUriList: MutableList<Uri> = mutableListOf()

    fun handleViewModelEvent(test2ViewModelEvent: Test2ViewModelEvent) {
        when(test2ViewModelEvent) {
            is Test2ViewModelEvent.CallApiTest -> {
                callApiTest()
            }
            is Test2ViewModelEvent.SaveImage -> {
                imageUriList.clear()
                imageUriList = test2ViewModelEvent.images.toMutableList()

                imageUriList.forEach {
                    LogUtil.i_dev("ImageUriList: ${it}")
//                    val bitmap = InternalStorageUtil.getBitmapFromUri(this@InternalStorageActivity, it) ?:return@let
//                    val newUri = saveImage(applicationContext, BASE_IMAGE_PATH, InternalStorageUtil.getFileNameFromUri(applicationContext, it) ?:"", bitmap)
//                    LogUtil.i_dev("(한장) 새로운 URI ${newUri}")
//                    newUri?.let {
//                        LogUtil.i_dev("(한장) 새로운 절대경로: ${InternalStorageUtil.getAbsolutePath(applicationContext, BASE_IMAGE_PATH, it)}")
//                    }
                }
            }
        }
    }

    private fun callApiTest() {
        scopeJob?.cancel()
        scopeJob = scope.launch {
            requestSignUp(
                ReqSignUp(
                    phoneNumber = "01025399533",
                    password = "password1!",
                    email = "jongukjeon@confitech.co.kr",
                    termIDs = emptyList(),
                    birthday = "250101",
                    name = "종욱전",
                    sex = "M"
                )
            )

            requestLogin("jongukjeon@confitech.co.kr", "password1!")

            requestResignation(
                ReqResignation(
                    channel = "app_test",
                    reason = "Test",
                    memberId = memberId
                )
            )
        }
    }

    private suspend fun requestLogin(id: String, password: String) {

        if(!networkManager.checkNetworkState()) {
            return
        }

        postRequestLoginUseCase.invoke(ReqLogin(id, password))
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

                accessToken = "Bearer ${it.resultData?.access_token}"

                val decoded = JwtUtil.decodeJWT(it.resultData?.access_token)
                if (decoded.containsKey("error")) {
                    LogUtil.e_dev("Decoding failed: ${decoded["error"]}")
                } else {
                    decoded.forEach { (key, value) ->
                        LogUtil.d_dev("$key: $value")
                        if(key == "sub") {
                            memberId = value.toString()
                        }
                    }
                }
            }
    }


    private suspend fun requestLogout(token: String) {

        if(!networkManager.checkNetworkState()) {
            return
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
            }
    }

    private suspend fun requestSignUp(reqSignUp: ReqSignUp) {
        if(!networkManager.checkNetworkState()) {
            return
        }

        postRequestSignUpUseCase.invoke(reqSignUp = reqSignUp)
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
                LogUtil.d_dev("회원가입 결과: ${it.resultData}")
            }
    }

    private suspend fun requestResignation(reqResignation: ReqResignation) {
        if(!networkManager.checkNetworkState()) {
            return
        }

        postRequestResignationUseCase.invoke(token = accessToken?:return, reqResignation = reqResignation)
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
                LogUtil.d_dev("회원 탈퇴 요청 결과: ${it.resultData}")
            }
    }

}