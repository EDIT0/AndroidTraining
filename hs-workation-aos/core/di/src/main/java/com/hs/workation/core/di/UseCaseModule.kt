package com.hs.workation.core.di

import com.hs.workation.domain.repository.AuthRepository
import com.hs.workation.domain.repository.BaseRepository
import com.hs.workation.domain.repository.TestRepository
import com.hs.workation.domain.usecase.DeleteTest2QuestionUseCase
import com.hs.workation.domain.usecase.GetTest1UseCase
import com.hs.workation.domain.usecase.GetTest2UseCase
import com.hs.workation.domain.usecase.PostRequestLoginUseCase
import com.hs.workation.domain.usecase.PostRequestLogoutUseCase
import com.hs.workation.domain.usecase.PostRequestResignationUseCase
import com.hs.workation.domain.usecase.PostRequestSignUpUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun providesGetTest1UseCase(
        testRepository: TestRepository
    ): GetTest1UseCase {
        return GetTest1UseCase(testRepository)
    }

    @Provides
    @Singleton
    fun providesGetTest2UseCase(
        testRepository: TestRepository
    ): GetTest2UseCase {
        return GetTest2UseCase(testRepository)
    }

    @Provides
    @Singleton
    fun providesDeleteTest2QuestionUseCase(
        testRepository: TestRepository
    ): DeleteTest2QuestionUseCase {
        return DeleteTest2QuestionUseCase(testRepository)
    }

    @Singleton
    @Provides
    fun providesPostRequestLoginUseCase(
        authRepository: AuthRepository
    ): PostRequestLoginUseCase {
        return PostRequestLoginUseCase(authRepository)
    }

    @Singleton
    @Provides
    fun providesPostRequestLogoutUseCase(
        authRepository: AuthRepository
    ): PostRequestLogoutUseCase {
        return PostRequestLogoutUseCase(authRepository)
    }

    @Singleton
    @Provides
    fun providesPostRequestSignUpUseCase(
        baseRepository: BaseRepository
    ): PostRequestSignUpUseCase {
        return PostRequestSignUpUseCase(baseRepository)
    }

    @Singleton
    @Provides
    fun providesPostRequestResignationUseCase(
        baseRepository: BaseRepository
    ): PostRequestResignationUseCase {
        return PostRequestResignationUseCase(baseRepository)
    }
}