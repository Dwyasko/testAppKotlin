package com.yascode.testapp.data.remote

/**
 * Created by caksono21 on 29/11/17.
 */
class ContentResponse(val data: List<ContentItem>)

class ContentItem(
        val ImgPath: String,
        val imgDesc: String,
        val imgSum: String
)

class AuthResponse(val data: Auth)

class Auth(val access_token: String)