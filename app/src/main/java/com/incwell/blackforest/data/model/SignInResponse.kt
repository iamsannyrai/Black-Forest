package com.incwell.blackforest.data.model

data class SignInResponse(
    val first_name: String,
    val last_name: String,
    val username: String,
    val token: String,
    val email: String
)