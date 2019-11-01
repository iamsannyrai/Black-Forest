package com.incwell.blackforest.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.incwell.blackforest.LOG_TAG
import com.incwell.blackforest.data.model.*
import com.incwell.blackforest.data.repository.AuthenticationRepository
import com.incwell.blackforest.tokenKey
import com.incwell.blackforest.util.NoInternetException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import java.net.SocketTimeoutException

class AuthenticationViewModel(private val authenticationRepository: AuthenticationRepository) :
    ViewModel() {

    var isPresent: Boolean? = false
    var userToken: UserToken? = null

    private val _loginResponse = MutableLiveData<SignInResponse>()
    val loginResponse: LiveData<SignInResponse>
        get() = _loginResponse

    private val _signupResponse = MutableLiveData<User>()
    val signupResponse: LiveData<User>
        get() = _signupResponse

    private val _messageResponse = MutableLiveData<String>()
    val messageResponse: LiveData<String>
        get() = _messageResponse


    fun sharedPreference() {
        isPresent = authenticationRepository.checkCredential(tokenKey)
        if (isPresent == true) {
            userToken = authenticationRepository.getCredential(tokenKey)
        }
    }

    fun registerUser(
        mUsername: String,
        mFirstname: String,
        mLastname: String,
        mEmail: String,
        mPassword: String,
        mCity: Int,
        mAddress: String,
        mPhone: String
    ) {
        val newUser =
            User(mUsername, mFirstname, mLastname, mEmail, mPassword, mCity, mAddress, mPhone)
        Log.i(LOG_TAG, "$newUser")
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = authenticationRepository.registerUser(newUser)
                if (response.isSuccessful) {
                    _signupResponse.postValue(response.body()!!.data)
                } else {
                    _messageResponse.postValue("Something went wrong!")
                }
            } catch (e: NoInternetException) {
                _messageResponse.postValue(e.message)
            } catch (e: SocketTimeoutException) {
                _messageResponse.postValue(e.message)
            }
        }
    }

    fun signIn(username: String, password: String) {
        val credential = SignIn(username, password)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val signInResponse = authenticationRepository.signinUser(credential)
                if (signInResponse.isSuccessful) {
                    authenticationRepository.saveCredential(
                        tokenKey,
                        UserToken(signInResponse.body()!!.data!!.token)
                    )
                    _loginResponse.postValue(signInResponse.body()!!.data)
                } else {
                    _messageResponse.postValue("Invalid credentials!")
                }
            } catch (e: NoInternetException) {
                _messageResponse.postValue(e.message)
            } catch (e: SocketTimeoutException) {
                _messageResponse.postValue(e.message)
            }
        }
    }

    fun onLogoutButtonClicked() {
        authenticationRepository.deleteCredential(tokenKey)
    }
}