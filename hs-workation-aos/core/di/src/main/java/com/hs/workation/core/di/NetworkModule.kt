package com.hs.workation.core.di

import android.content.Context
import com.google.gson.GsonBuilder
import com.hs.workation.core.common.constants.NetworkConstants.AUTH_OK_HTTP_CLIENT
import com.hs.workation.core.common.constants.NetworkConstants.AUTH_RETROFIT
import com.hs.workation.core.common.constants.NetworkConstants.BASE_OK_HTTP_CLIENT
import com.hs.workation.core.common.constants.NetworkConstants.BASE_RETROFIT
import com.hs.workation.core.util.LogUtil
import com.hs.workation.core.util.NetworkManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private val nullOnEmptyConverterFactory = object : Converter.Factory() {
        fun converterFactory() = this
        override fun responseBodyConverter(
            type: Type,
            annotations: Array<out Annotation>,
            retrofit: Retrofit
        ) = object : Converter<ResponseBody, Any?> {
            val nextResponseBodyConverter = retrofit.nextResponseBodyConverter<Any?>(converterFactory(), type, annotations)
            override fun convert(value: ResponseBody) = if (value.contentLength() != 0L) nextResponseBodyConverter.convert(value) else null
        }
    }

    @Singleton
    @Provides
    fun providesContext(@ApplicationContext context: Context): Context {
        return context
    }

    @Singleton
    @Provides
    fun providesNetworkManager(context: Context): NetworkManager {
        return NetworkManager(context)
    }

    class SetAuthHeader(): Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val newRequest = chain.request().newBuilder()
                .addHeader("Content-Type", "application/json")

            return chain.proceed(newRequest.build())
        }
    }

    @Singleton
    @Provides
    fun providesSetAuthHeader(): SetAuthHeader {
        return SetAuthHeader()
    }

    class SetBaseHeader(): Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val newRequest = chain.request().newBuilder()
                .addHeader("Content-Type", "application/json")

            return chain.proceed(newRequest.build())
        }
    }

    @Singleton
    @Provides
    fun providesSetBaseHeader(): SetBaseHeader {
        return SetBaseHeader()
    }

    @Singleton
    @Provides
    @Named(AUTH_OK_HTTP_CLIENT)
    fun providesAuthOkHttpClient(
        setAuthHeader: SetAuthHeader
    ): OkHttpClient {
        val builder = OkHttpClient.Builder()
        if(BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor {
                LogUtil.d_dev("[Network-Auth] ${it}")
            }
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(loggingInterceptor)
        }
        builder.apply {
            connectTimeout(10000L, TimeUnit.SECONDS)
            readTimeout(10000L, TimeUnit.SECONDS)
            writeTimeout(10000L, TimeUnit.SECONDS)
            addInterceptor(setAuthHeader)
        }

        return builder.build()
    }

    @Singleton
    @Provides
    @Named(BASE_OK_HTTP_CLIENT)
    fun providesBaseOkHttpClient(
        setBaseHeader: SetBaseHeader
    ): OkHttpClient {
        val builder = OkHttpClient.Builder()
        if(BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor {
                LogUtil.d_dev("[Network-Base] ${it}")
            }
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(loggingInterceptor)
        }
        builder.apply {
            connectTimeout(10000L, TimeUnit.SECONDS)
            readTimeout(10000L, TimeUnit.SECONDS)
            writeTimeout(10000L, TimeUnit.SECONDS)
            addInterceptor(setBaseHeader)
        }

        return builder.build()
    }

    @Singleton
    @Provides
    @Named(AUTH_RETROFIT)
    fun providesAuthRetrofit(
        @Named(AUTH_OK_HTTP_CLIENT) okHttpClient: OkHttpClient
    ): Retrofit {
        val gson = GsonBuilder()
            .disableHtmlEscaping() // HTML 문자 표시
            .setPrettyPrinting() // 문자열 가독성 향상
            .setLenient()
            .create()

        return Retrofit.Builder()
            .addConverterFactory(nullOnEmptyConverterFactory)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(BuildConfig.AUTH_URL)
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    @Named(BASE_RETROFIT)
    fun providesBaseRetrofit(
        @Named(BASE_OK_HTTP_CLIENT) okHttpClient: OkHttpClient
    ): Retrofit {
        val gson = GsonBuilder()
            .disableHtmlEscaping() // HTML 문자 표시
            .setPrettyPrinting() // 문자열 가독성 향상
            .setLenient()
            .create()

        return Retrofit.Builder()
            .addConverterFactory(nullOnEmptyConverterFactory)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .build()
    }
}