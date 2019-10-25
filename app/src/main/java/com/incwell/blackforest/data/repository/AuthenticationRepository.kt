package com.incwell.blackforest.data.repository

import com.incwell.blackforest.data.BlackForestService
import com.incwell.blackforest.data.model.SignIn
import com.incwell.blackforest.data.model.User
import com.incwell.blackforest.data.model.UserToken
import com.incwell.blackforest.data.storage.SharedPref

class AuthenticationRepository(private val blackForestService: BlackForestService) {

    fun saveCredential(key: String, token: UserToken) {
        return SharedPref.saveToken(key, token)
    }

    fun deleteCredential(key: String) {
        return SharedPref.deleteToken(key)
    }

    fun checkCredential(key: String): Boolean {
        return SharedPref.checkToken(key)
    }

    fun getCredential(key: String): UserToken {
        return SharedPref.getToken(key)
    }

    suspend fun registerUser(user: User) = blackForestService.registerUser(user)
    suspend fun signinUser(credential: SignIn) = blackForestService.signinUser(credential)
}