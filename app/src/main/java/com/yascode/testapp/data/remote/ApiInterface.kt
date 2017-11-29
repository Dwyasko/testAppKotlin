package com.yascode.testapp.data.remote

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST

/**
 * Created by caksono21 on 29/11/17.
 */
interface ApiInterface {

    @GET("data")
    fun getListImage(): Call<ContentResponse>

    @POST("data/create")
    @Multipart
    fun uploadImage(): Call<String>

    @POST("data/{id}/update")
    fun updateDetails(): Call<String>

    @POST("")
    fun login(username: String, password: String): Call<String>
}