package com.hs.workation.data.repository

import com.hs.workation.core.common.constants.NetworkConstants
import com.hs.workation.core.model.base.RequestResult
import com.hs.workation.core.model.dto.IdAndPassword
import com.hs.workation.core.model.dto.Jwt
import com.hs.workation.core.model.dto.ServiceResult
import com.hs.workation.data.datasource.remote.AuthRemoteDataSource
import com.hs.workation.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authRemoteDataSource: AuthRemoteDataSource
): AuthRepository {
    override suspend fun postRequestLogin(
        idAndPassword: IdAndPassword
    ): Flow<RequestResult<Jwt>> {
        return flow<RequestResult<Jwt>> {
            val response = authRemoteDataSource.postRequestLogin(idAndPassword)

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

    override suspend fun postRequestLogout(token: String): Flow<RequestResult<ServiceResult>> {
        return flow<RequestResult<ServiceResult>> {
            val response = authRemoteDataSource.postRequestLogout(token)

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