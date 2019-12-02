package com.incwell.blackforest.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.material.snackbar.Snackbar
import com.incwell.blackforest.LOG_TAG
import com.incwell.blackforest.data.model.*
import com.incwell.blackforest.data.repository.AuthenticationRepository
import com.incwell.blackforest.tokenKey
import com.incwell.blackforest.util.NoInternetException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException

class AuthenticationViewModel(private val authenticationRepository: AuthenticationRepository) :
    ViewModel() {

    var isPresent: Boolean? = false

    private val _status = MutableLiveData<Boolean>()
    val status: LiveData<Boolean>
        get() = _status

    private val _messageResponse = MutableLiveData<String>()
    val messageResponse: LiveData<String>
        get() = _messageResponse

    private val _userId = MutableLiveData<Int>()
    val userId: LiveData<Int>
        get() = _userId

    fun sharedPreference() {
        isPresent = authenticationRepository.checkCredential(tokenKey)
    }

    suspend fun getCities() = authenticationRepository.getCities()

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
                    _status.postValue(true)
                } else {
                    _status.postValue(false)
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
                    Log.d("userToken",signInResponse.body()!!.data!!.token)
                    authenticationRepository.saveCredential(
                        tokenKey,
                        signInResponse.body()!!.data!!.token
                    )
                    _status.postValue(true)
                } else {
                    _status.postValue(false)
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

    fun resetPassword(email: String) {
        val emailAdd = Email(email = email)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = authenticationRepository.resetPassword(emailAdd)
                if (response.isSuccessful) {
                    _messageResponse.postValue("Email has been sent for password reset! Please check your email.")
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

    fun getLink(link: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = authenticationRepository.getLink(link)
                if (response.isSuccessful) {
                    _userId.postValue(response.body()!!.data!!.id)
                } else {
                    Log.d("data-link", "${response}")
                }
            } catch (e: NoInternetException) {
                _messageResponse.postValue(e.message)
            } catch (e: SocketTimeoutException) {
                _messageResponse.postValue(e.message)
            }
        }
    }

    fun changePassword(newPassword: String, confirmPassword: String, id: Int) {
        val resetPassword =
            ResetPassword(new_password = newPassword, confirm_password = confirmPassword)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = authenticationRepository.changePassword(resetPassword, id)
                if (response.isSuccessful) {
                    _messageResponse.postValue("Password reset successfully")
                } else {
                    _messageResponse.postValue("Sorry your password couldn't be reset!")
                }
            } catch (e: NoInternetException) {
                _messageResponse.postValue(e.message)
            } catch (e: SocketTimeoutException) {
                _messageResponse.postValue(e.message)
            }
        }
    }
}