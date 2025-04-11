package com.hs.workation.core.common.constants

object NetworkConstants {
    // DI init retrofit const
    const val AUTH_RETROFIT = "AUTH_RETROFIT"
    const val AUTH_OK_HTTP_CLIENT = "AUTH_OK_HTTP_CLIENT"

    const val BASE_RETROFIT = "BASE_RETROFIT"
    const val BASE_OK_HTTP_CLIENT = "BASE_OK_HTTP_CLIENT"

    // HTTP 상태 코드
    const val SUCCESS_GET = 200 // Get 요청 성공
    const val SUCCESS_PUT = 200 // Put 요청 성공
    const val SUCCESS_POST = 201 // Post 요청 성공
    const val SUCCESS_DELETE = 204 // Delete 요청 성공

    const val ERROR_BAD_REQUEST = 400 // Bad Request
    const val ERROR_UNAUTHORIZED = 401 // Unauthorized
    const val ERROR_FORBIDDEN = 403 // Forbidden
    const val ERROR_NOT_FOUND = 404 // Not Found
    const val ERROR_NOT_PROVIDE_API = 405 // API 제공 X
    const val ERROR_USING_TIME_CRASH = 409 // 예약 시간 충돌, 이용 시간 충돌 등

    const val ERROR_INTERNAL_SERVER = 500 // Internal Server Error


}