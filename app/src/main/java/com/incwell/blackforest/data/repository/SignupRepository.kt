package com.incwell.blackforest.data.repository

import com.incwell.blackforest.data.BlackForestService
import com.incwell.blackforest.data.model.User

class SignupRepository(private val blackForestService: BlackForestService) {
    suspend fun registerUser(user:User)= blackForestService.registerUser(user)
}