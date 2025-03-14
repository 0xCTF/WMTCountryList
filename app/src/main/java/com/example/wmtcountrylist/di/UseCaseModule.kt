package com.example.wmtcountrylist.di

import com.example.wmtcountrylist.domain.GetCountriesUseCase
import com.example.wmtcountrylist.domain.GetCountriesUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class UseCaseModule {

    @Binds
    abstract fun bindGetCountriesUseCase(
        impl: GetCountriesUseCaseImpl
    ): GetCountriesUseCase
}