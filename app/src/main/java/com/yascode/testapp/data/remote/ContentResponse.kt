package com.yascode.testapp.data.remote

/**
 * Created by caksono21 on 29/11/17.
 */
class ContentResponse(val data: List<ContentItem>)

class ContentItem(
        val id:Int,
        val summary: String,
        val thumbnail_url: String
)

class AuthResponse(val access_token: String)

class UploadResponse(val id: Int,
                     val thumbnail_url: String,
                     val original_url: String,
                     val summary: String,
                     val detail: String)