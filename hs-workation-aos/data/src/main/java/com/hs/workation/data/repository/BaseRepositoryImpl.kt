package com.hs.workation.data.repository

import com.hs.workation.core.common.constants.NetworkConstants
import com.hs.workation.core.model.base.RequestResult
import com.hs.workation.core.model.dto.req.ReqResignation
import com.hs.workation.core.model.dto.req.ReqSignUp
import com.hs.workation.core.model.dto.res.ResResignation
import com.hs.workation.core.model.dto.res.ResSignUp
import com.hs.workation.data.datasource.remote.BaseRemoteDataSource
import com.hs.workation.domain.repository.BaseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class BaseRepositoryImpl @Inject constructor(
    private val baseRemoteDataSource: BaseRemoteDataSource
): BaseRepository {

    override suspend fun postRequestSignUp(reqSignUp: ReqSignUp): Flow<RequestResult<ResSignUp>> {
        return flow<RequestResult<ResSignUp>> {
            val response = baseRemoteDataSource.postRequestSignUp(reqSignUp)

            val body = response.body()

            if(response.isSuccessful) {
                if(body == null) {
                    emit(RequestResult.DataEmpty())
                } else {
                    emit(RequestResult.Success(data = body))
                }
            } else {
                when(response.code()) {
                    NetworkConstants.ERROR_BAD_REQUEST -> {
                        emit(RequestResult.Error(code = NetworkConstants.ERROR_BAD_REQUEST, message = response.message()))
                    }
                    NetworkConstants.ERROR_UNAUTHORIZED -> {
                        emit(RequestResult.Error(code = NetworkConstants.ERROR_UNAUTHORIZED, message = response.message()))
                    }
                    NetworkConstants.ERROR_FORBIDDEN -> {
                        emit(RequestResult.Error(code = NetworkConstants.ERROR_FORBIDDEN, message = response.message()))
                    }
                    NetworkConstants.ERROR_NOT_FOUND -> {
                        emit(RequestResult.Error(code = NetworkConstants.ERROR_NOT_FOUND, message = response.message()))
                    }
                    NetworkConstants.ERROR_NOT_PROVIDE_API -> {
                        emit(RequestResult.Error(code = NetworkConstants.ERROR_NOT_PROVIDE_API, message = response.message()))
                    }
                    NetworkConstants.ERROR_INTERNAL_SERVER -> {
                        emit(RequestResult.Error(code = NetworkConstants.ERROR_INTERNAL_SERVER, message = response.message()))
                    }
                }
            }
        }.catch {
            throw Exception(it)
        }
    }

    override suspend fun postRequestResignation(
        token: String,
        reqResignation: ReqResignation
    ): Flow<RequestResult<ResResignation>> {
        return flow<RequestResult<ResResignation>> {
            val response = baseRemoteDataSource.postRequestResignation(token, reqResignation)

            val body = response.body()

            if(response.isSuccessful) {
                if(body == null) {
                    emit(RequestResult.DataEmpty())
                } else {
                    emit(RequestResult.Success(data = body))
                }
            } else {
                when(response.code()) {
                    NetworkConstants.ERROR_BAD_REQUEST -> {
                        emit(RequestResult.Error(code = NetworkConstants.ERROR_BAD_REQUEST, message = response.message()))
                    }
                    NetworkConstants.ERROR_UNAUTHORIZED -> {
                        emit(RequestResult.Error(code = NetworkConstants.ERROR_UNAUTHORIZED, message = response.message()))
                    }
                    NetworkConstants.ERROR_FORBIDDEN -> {
                        emit(RequestResult.Error(code = NetworkConstants.ERROR_FORBIDDEN, message = response.message()))
                    }
                    NetworkConstants.ERROR_NOT_FOUND -> {
                        emit(RequestResult.Error(code = NetworkConstants.ERROR_NOT_FOUND, message = response.message()))
                    }
                    NetworkConstants.ERROR_NOT_PROVIDE_API -> {
                        emit(RequestResult.Error(code = NetworkConstants.ERROR_NOT_PROVIDE_API, message = response.message()))
                    }
                    NetworkConstants.ERROR_INTERNAL_SERVER -> {
                        emit(RequestResult.Error(code = NetworkConstants.ERROR_INTERNAL_SERVER, message = response.message()))
                    }
                }
            }
        }.catch {
            throw Exception(it)
        }
    }
}