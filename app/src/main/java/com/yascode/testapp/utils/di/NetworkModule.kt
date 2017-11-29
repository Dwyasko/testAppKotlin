package com.yascode.testapp.utils.di

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

/**
 * Created by caksono21 on 29/11/17.
 */
@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
                .baseUrl("https://test-mobile.neo-fusion.com/")
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
    }
}