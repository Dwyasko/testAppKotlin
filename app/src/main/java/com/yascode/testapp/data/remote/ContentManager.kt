package com.yascode.testapp.data.remote

import com.yascode.testapp.data.local.Content
import ru.gildor.coroutines.retrofit.Result
import ru.gildor.coroutines.retrofit.awaitResult
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by caksono21 on 29/11/17.
 */
@Singleton
class ContentManager @Inject constructor(private val api: ImageApi) {

    suspend fun getListImage(token: String): List<Content> {
        val result = api.getListImage(token).awaitResult()

        return when (result) {
            is Result.Ok -> processList(result.value)
            is Result.Error -> throw Throwable("HTTP Error: ${result.response.message()}")
            is Result.Exception -> throw result.exception
            else -> {
                throw Throwable("Unknown Error Occured")
            }
        }
    }

    fun processList(response: List<ContentItem>): List<Content> {
        val content = response.map {
            Content(it.id, it.summary, "", it.thumbnail_url, "")
        }
        return content
    }

    suspend fun postLogin(username: String, password: String): String {
        val result = api.login(username, password).awaitResult()

        return when (result) {
            is Result.Ok -> result.value.access_token
            is Result.Error -> throw Throwable("HTTP Error: ${result.response.message()}")
            is Result.Exception -> throw result.exception
            else -> {
                throw Throwable("Unknown Error Occured")
            }
        }
    }


    suspend fun postContent(token: String, fileName: String, detail: Detail?): Boolean {
        val file = File(fileName)
        val result = api.postImage(token, file).awaitResult()

        return when (result) {
            is Result.Ok -> if (detail == null) true else processUpdateDetail(result.value.id, detail, token)
            is Result.Error -> false/*throw Throwable("HTTP Error: ${result.response.message()}")*/
            is Result.Exception -> false
            else -> {
                false
                throw Throwable("Unknown Error Occured")
            }
        }
    }

    suspend fun processUpdateDetail(id: Int, detail: Detail?, token: String): Boolean {
        val result = api.postDetails(token, id, detail!!).awaitResult()

        return when (result) {
            is Result.Ok -> true
            is Result.Error -> throw Throwable("HTTP Error: ${result.response.message()}")
            is Result.Exception -> throw result.exception
            else -> {
                false
                throw Throwable("Unknown Error Occured")
            }
        }
    }

    suspend fun getContent(id: Int, token: String): Content {
        var result = api.getContent(id, token).awaitResult()

        return when (result) {
            is Result.Ok -> mappingToContent(result.value)
            is Result.Error -> throw Throwable("HTTP Error: ${result.response.message()}")
            is Result.Exception -> throw result.exception
            else -> {
                false
                throw Throwable("Unknown Error Occured")
            }
        }
    }

    private fun mappingToContent(value: UploadResponse): Content {
        return Content(value.id, value.summary, value.detail, value.thumbnail_url, value.original_url)
    }
}