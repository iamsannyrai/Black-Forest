package com.incwell.blackforest.data.repository

import com.incwell.blackforest.data.AuthenticationService
import com.incwell.blackforest.data.model.Email
import com.incwell.blackforest.data.model.ResetPassword
import com.incwell.blackforest.data.model.SignIn
import com.incwell.blackforest.data.model.User
import com.incwell.blackforest.data.storage.SharedPref

class AuthenticationRepository(private val authenticationService: AuthenticationService) {

    fun saveCredential(key: String, token: String) {
        return SharedPref.saveToken(key, token)
    }

    fun deleteCredential(key: String) {
        return SharedPref.deleteToken(key)
    }

    fun checkCredential(key: String): Boolean {
        return SharedPref.checkToken(key)
    }

    suspend fun getCities() = authenticationService.getCities()

    suspend fun registerUser(user: User) = authenticationService.registerUser(user)
    suspend fun signinUser(credential: SignIn) = authenticationService.signinUser(credential)

    suspend fun resetPassword(email: Email)=authenticationService.resetPassword(email)

    suspend fun getLink(link:String)=authenticationService.getlinkFromUri(link)

    suspend fun changePassword(resetPassword:ResetPassword,id:Int) = authenticationService.resetPasswordDone(resetPassword,id)
}