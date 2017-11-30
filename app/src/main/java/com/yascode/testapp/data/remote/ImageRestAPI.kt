package com.yascode.testapp.data.remote

import okhttp3.MediaType
import okhttp3.MultipartBody
import retrofit2.Call
import java.io.File
import javax.inject.Inject
import okhttp3.RequestBody


/**
 * Created by caksono21 on 29/11/17.
 */
class ImageRestApi @Inject constructor(private val api: ApiInterface) : ImageApi {
    override fun getListImage(token: String): Call<List<ContentItem>> {
        return api.getListImage(token)
    }

    override fun login(username: String, password: String): Call<AuthResponse> {
        val user = User(username, password)
        return api.login(user)
    }

    override fun postImage(token: String, filename: File): Call<UploadResponse> {
        val requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), filename)
        return api.uploadImage(token, MultipartBody.Part.createFormData("file", filename.path, requestFile))
    }

    override fun postDetails(token: String, id: Int, detail: Detail): Call<UploadResponse> {
        return api.updateDetails(token, id, detail)
    }

    override fun getContent(id: Int, token: String): Call<UploadResponse> {
        return api.getContent(token, id)
    }
}