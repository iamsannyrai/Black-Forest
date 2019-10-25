package com.incwell.blackforest.data.repository

import com.incwell.blackforest.data.BlackForestService
import com.incwell.blackforest.data.model.SignIn
import com.incwell.blackforest.data.model.User

class AuthenticationRepository(private val blackForestService: BlackForestService) {
    suspend fun registerUser(user: User) = blackForestService.registerUser(user)
    suspend fun signinUser(credential: SignIn) = blackForestService.signinUser(credential)
}