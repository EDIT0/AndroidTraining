package com.example.roomdemo1.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.example.roomdemo1.SingleLiveEvent
import com.example.roomdemo1.data.model.User
import com.example.roomdemo1.data.util.ERROR
import com.example.roomdemo1.domain.usecase.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class MainViewModel(
    private val getAllUserUseCase: GetAllUserUseCase,
    private val addUserUseCase: AddUserUseCase,
    private val deleteUserUseCase: DeleteUserUseCase,
    private val updateUserUseCase: UpdateUserUseCase,
    private val selectUserUseCase: SelectUserUseCase
) : ViewModel() {

    private val TAG = MainViewModel::class.java.simpleName

    private val _response = SingleLiveEvent<ResponseSet>()
    val response: LiveData<ResponseSet> get() = _response

    private var _searchList : MutableLiveData<List<User>> = MutableLiveData<List<User>>()
    val searchList: LiveData<List<User>> = _searchList

    private var _userList = getAllUsers().asLiveData()
    val userList: LiveData<List<User>> = _userList

    fun getAllUsers() = getAllUserUseCase.execute()

    fun add(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            val apiResult = addUserUseCase.execute(user)
            apiResult
                .catch {
                    Log.i(TAG, "add error : ${it}")
                }
                .collect {
                    Log.i(TAG, "add collect : ${it}")
                }
        }
    }

    fun delete(id: Int) {
        viewModelScope.launch {
            val apiResult = deleteUserUseCase.execute(id)
            apiResult
                .catch {
                    Log.i(TAG, "delete error : ${it}")
                }
                .collect {
                    Log.i(TAG, "delete collect : ${it}")
                    if(it == 0) {
                        _response.value = ResponseSet.ERROR
                    } else {
                        _response.value = ResponseSet.SUCCESS
                    }

                }

        }
    }

    fun update(id: Int, newName: String) {
        viewModelScope.launch {
            val apiResult = updateUserUseCase.execute(id, newName)
            apiResult
                .catch {
                    Log.i(TAG, "update error : ${it}")
                }
                .collect {
                    Log.i(TAG, "update collect : ${it}")
                    if(it == 0) {
                        _response.value = ResponseSet.ERROR
                    } else {
                        _response.value = ResponseSet.SUCCESS
                    }
                }

        }
    }

    fun select(name: String) {
        viewModelScope.launch {
            val apiResult = selectUserUseCase.execute(name)
            apiResult
                .catch {
                    Log.i(TAG, "select error : ${it}")
                    if(it.message == ERROR) {
                        _response.value = ResponseSet.ERROR
                    }
                }
                .collect {
                    Log.i(TAG, "select collect : ${it}")
                    _searchList.value = it as ArrayList
                    _response.value = ResponseSet.SUCCESS
                }

        }
    }

    enum class ResponseSet {
        SUCCESS,
        ERROR
    }
}