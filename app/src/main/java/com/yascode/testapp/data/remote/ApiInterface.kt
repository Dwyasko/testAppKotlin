package com.yascode.testapp.data.remote

import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

/**
 * Created by caksono21 on 29/11/17.
 */
interface ApiInterface {

    @GET("data")
    fun getListImage(@Header("Access-Token") token: String): Call<List<ContentItem>>

    @GET("data/{id}")
    fun getContent(@Header("Access-Token") token: String, @Path("id") id: Int): Call<UploadResponse>

    @Multipart
    @POST("data/create")
    fun uploadImage(@Header("Access-Token") token: String, @Part file: MultipartBody.Part): Call<UploadResponse>

    @POST("data/{id}/update")
    fun updateDetails(@Header("Access-Token") token: String, @Path("id") id: Int, @Body detail: Detail): Call<UploadResponse>

    @POST("/auth/login")
    fun login(@Body user: User): Call<AuthResponse>


}