package com.yascode.testapp.data.remote

import com.yascode.testapp.data.local.Content
import ru.gildor.coroutines.retrofit.Result
import ru.gildor.coroutines.retrofit.awaitResult
import rx.Observable
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by caksono21 on 29/11/17.
 */
@Singleton
class ContentManager @Inject constructor(private val api: ImageApi) {

    suspend fun getListImage(): List<Content> {
        val result = api.getListImage().awaitResult()

        return when (result) {
            is Result.Ok -> processList(result.value)
            is Result.Error -> throw Throwable("HTTP Error: ${result.response.message()}")
            is Result.Exception -> throw result.exception
            else -> {
                throw Throwable("Unknown Error Occured")
            }
        }
    }

    fun processList(response: ContentResponse): List<Content> {
        val content = response.data?.map {
            Content(it.ImgPath, it.imgDesc, it.imgSum)
        }

        return content
    }

    suspend fun postLogin(username: String, password: String): String {
        val result = api.login(username, password).awaitResult()

        return when (result) {
            is Result.Ok -> result.value
            is Result.Error -> throw Throwable("HTTP Error: ${result.response.message()}")
            is Result.Exception -> throw result.exception
            else -> {
                throw Throwable("Unknown Error Occured")
            }
        }
    }


}