package com.yascode.testapp.data.remote

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.File
import javax.inject.Inject

/**
 * Created by caksono21 on 29/11/17.
 */
class ImageRestApi @Inject constructor(private val api: ApiInterface) : ImageApi {
    override fun getListImage(): Call<ContentResponse> {
        return api.getListImage()
    }

    override fun login(username: String, password: String): Call<String> {
        return api.login(username, password)
    }

    override fun postImage(file: File) {
    }

    override fun postDetails(strDesc: String, strSum: String) {
    }

    override fun getImageDetail(id: String) {
    }
}