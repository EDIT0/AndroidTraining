package com.hs.workation.feature.splash.test1.view.viewmodel

import android.app.Application
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.hs.workation.core.base.viewmodel.BaseAndroidViewModel
import com.hs.workation.core.common.R
import com.hs.workation.core.util.LogUtil
import com.hs.workation.core.util.NetworkManager
import com.hs.workation.core.model.base.RequestResult
import com.hs.workation.core.model.base.SideEffectEvent
import com.hs.workation.core.model.test.res.ResTest2
import com.hs.workation.domain.usecase.DeleteTest2QuestionUseCase
import com.hs.workation.domain.usecase.GetTest1UseCase
import com.hs.workation.domain.usecase.GetTest2UseCase
import com.hs.workation.feature.splash.test1.event.Test1NoStateUiEvent
import com.hs.workation.feature.splash.test1.event.Test1UiErrorEvent
import com.hs.workation.feature.splash.test1.event.Test1UiEvent
import com.hs.workation.feature.splash.test1.event.Test1ViewModelEvent
import com.hs.workation.feature.splash.test1.state.Test1UiState
import com.hs.workation.feature.splash.test1.view.adapter.Test1Adapter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.runningFold
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class Test1ViewModel @Inject constructor(
    app: Application,
    networkManager: NetworkManager,
    private val getTest1UseCase: GetTest1UseCase,
    private val getTest2UseCase: GetTest2UseCase,
    private val deleteTest2QuestionUseCase: DeleteTest2QuestionUseCase
) : BaseAndroidViewModel(app, networkManager) {

    private val scope = viewModelScope

    private var invokeGetTest1UseCaseJob: Job? = null
    private var invokeGetTest2UseCaseJob: Job? = null
    private var invokeDeleteTest2QuestionUseCaseJob: Job? = null

    private val _sideEffectEvent = Channel<SideEffectEvent>()
    val sideEffectEvent = _sideEffectEvent.receiveAsFlow()

    // 서버로 부터 가져오는 데이터 ResTest2에 Paging에 사용되는 데이터와 아닌 데이터가 있으므로,
    // Paging을 제외한 데이터를 가져와 사용하기 위한 변수
    private val _saveResTest2 = MutableStateFlow<ResTest2>(ResTest2())

    private val _test1UiState = MutableStateFlow<Test1UiEvent>(Test1UiEvent.Idle)
    val test1UiState: StateFlow<Test1UiState> = _test1UiState
        .runningFold(Test1UiState()) { state, event ->
            when(event) {
                is Test1UiEvent.Idle -> {
                    state
                }
                is Test1UiEvent.UpdateQuestions -> {
                    state.copy(questions = event.questions)
                }
                is Test1UiEvent.UpdateResTest2 -> {
                    state.copy(resTest2 = event.resTest2)
                }
                is Test1UiEvent.UpdateResTest1 -> {
                    state.copy(resTest1 = event.resTest1)
                }
            }
        }.stateIn(scope, SharingStarted.Eagerly, Test1UiState())

    private val _test1NoStateUiEvent = MutableSharedFlow<Test1NoStateUiEvent>()
    val test1NoStateUiEvent = _test1NoStateUiEvent.asSharedFlow()

    private val _test1UiErrorEvent = MutableSharedFlow<Test1UiErrorEvent>()
    val test1UiErrorEvent = _test1UiErrorEvent

    val test1Adapter =
        Test1Adapter()

    init {
        scope.launch {
            _saveResTest2.collect {
                _test1UiState.emit(Test1UiEvent.UpdateResTest2(resTest2 = it))
            }
        }
    }

    fun handleViewModelEvent(test1ViewModelEvent: Test1ViewModelEvent) {
        when (test1ViewModelEvent) {
            is Test1ViewModelEvent.GetTest1 -> {
                invokeGetTest1UseCaseJob?.cancel()
                invokeGetTest1UseCaseJob = scope.launch {
                    if(!networkCheck()) {
                        _sideEffectEvent.send(SideEffectEvent.NetworkError(message = app.getString(R.string.network_connection_check)))
                        return@launch
                    }

                    getTest1UseCase.invoke()
                        .filter {
                            val errorCode = "${it.code}"
                            val errorMessage = it.message

                            if(it is RequestResult.Error) {
                                _test1UiErrorEvent.emit(Test1UiErrorEvent.Fail(code = errorCode, message = errorMessage))
                            }

                            if(it is RequestResult.DataEmpty) {
                                _test1UiErrorEvent.emit(Test1UiErrorEvent.DataEmpty())
                            }

                            return@filter it is RequestResult.Success
                        }
                        .catch {
                            // 에러 발생
                            LogUtil.d_dev("Catch Exception : ${it}")
                            _test1UiErrorEvent.emit(Test1UiErrorEvent.ExceptionHandle(it))
                        }
                        .collect {
                            // 결과
                            LogUtil.d_dev("GetTest1 : ${it.code}/${it.message}/${it.resultData}")
                            it.resultData?.let { resTest1 ->
                                _test1UiState.emit(Test1UiEvent.UpdateResTest1(resTest1 = resTest1))
                            }
                        }
                }
            }
            is Test1ViewModelEvent.GetTest2 -> {
                invokeGetTest2UseCaseJob?.cancel()
                invokeGetTest2UseCaseJob = scope.launch {
                    if(!networkManager.checkNetworkState()) {
                        _sideEffectEvent.send(SideEffectEvent.NetworkError(app.getString(R.string.network_connection_check)))
                        return@launch
                    }

                    getTest2UseCase.invoke(test1ViewModelEvent.reqTest2, _saveResTest2)
                        .cachedIn(scope)
                        .map { it ->
                            it.map { model ->
                                LogUtil.d_dev("페이징 데이터:  ${model}")
                                model
                            }
                        }
                        .catch {
                            // 에러 발생
                            LogUtil.d_dev("Catch Exception : ${it}")
                            _test1UiErrorEvent.emit(Test1UiErrorEvent.ExceptionHandle(it))
                        }
                        .collect {
                            _test1UiState.emit(Test1UiEvent.UpdateQuestions(questions = it))
                        }
                }
            }
            is Test1ViewModelEvent.DeleteTest2Question -> {
                invokeDeleteTest2QuestionUseCaseJob?.cancel()
                invokeDeleteTest2QuestionUseCaseJob = scope.launch {
                    if(!networkCheck()) {
                        _sideEffectEvent.send(SideEffectEvent.NetworkError(message = app.getString(R.string.network_connection_check)))
                        return@launch
                    }

                    deleteTest2QuestionUseCase.invoke(test1ViewModelEvent.question)
                        .filter {
                            val errorCode = "${it.code}"
                            val errorMessage = it.message

                            if(it is RequestResult.Error) {
                                _test1UiErrorEvent.emit(Test1UiErrorEvent.Fail(code = errorCode, message = errorMessage))
                            }

                            if(it is RequestResult.DataEmpty) {
                                _test1UiErrorEvent.emit(Test1UiErrorEvent.DataEmpty())
                            }

                            return@filter it is RequestResult.Success
                        }
                        .catch {
                            // 에러 발생
                            LogUtil.d_dev("Catch Exception : ${it}")
                            _test1UiErrorEvent.emit(Test1UiErrorEvent.ExceptionHandle(it))
                        }
                        .collect {
                            // 결과
                            LogUtil.d_dev("DeleteTest2Question : ${it.code}/${it.message}/${it.resultData}")
                            it.resultData?.let { isDelete ->
                                _test1NoStateUiEvent.emit(Test1NoStateUiEvent.UpdateIsDeleteListItem(isDelete))
                            }
                        }
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()

        _sideEffectEvent.close()
    }
}