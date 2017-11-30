package com.yascode.testapp.di

import com.yascode.testapp.data.remote.ApiInterface
import com.yascode.testapp.data.remote.ImageApi
import com.yascode.testapp.data.remote.ImageRestApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * Created by caksono21 on 30/11/17.
 */
@Module
class ContentModule {

    @Provides
    @Singleton
    fun provideImageApi(api: ApiInterface): ImageApi = ImageRestApi(api)

    @Provides
    @Singleton
    fun provideApiInterface(retrofit : Retrofit) : ApiInterface = retrofit.create(ApiInterface::class.java)
}