package com.yascode.testapp.data.remote

import retrofit2.Call
import java.io.File

/**
 * Created by caksono21 on 29/11/17.
 */
interface ImageApi {
    fun getListImage(token: String): Call<List<ContentItem>>

    fun getContent(id: Int, token: String): Call<UploadResponse>

    fun login(username: String, password: String): Call<AuthResponse>

    fun postImage(token: String, filename: File): Call<UploadResponse>

    fun postDetails(token: String, id: Int, detail: Detail): Call<UploadResponse>
}