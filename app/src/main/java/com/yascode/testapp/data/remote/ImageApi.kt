package com.yascode.testapp.data.remote

import retrofit2.Call
import java.io.File

/**
 * Created by caksono21 on 29/11/17.
 */
interface ImageApi {
    fun getListImage(): Call<ContentResponse>
    fun getImageDetail(id: String)

    fun login(username: String, password: String) : Call<String>

    fun postImage(file: File)

    fun postDetails(strDesc: String, strSum: String)
}