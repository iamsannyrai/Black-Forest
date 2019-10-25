package com.incwell.blackforest.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.incwell.blackforest.LOG_TAG
import com.incwell.blackforest.data.model.SignIn
import com.incwell.blackforest.data.model.User
import com.incwell.blackforest.data.repository.AuthenticationRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AuthenticationViewModel(private val authenticationRepository: AuthenticationRepository) : ViewModel() {

    private val _response = MutableLiveData<Boolean>()
    val response: LiveData<Boolean>
        get() = _response

    private val _responseMessage = MutableLiveData<String>()
    val responseMessage: LiveData<String>
        get() = _responseMessage

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
            val response = authenticationRepository.registerUser(newUser)
            if (response.isSuccessful) {
                _response.postValue(response.body()?.status)
                Log.i(LOG_TAG, "${response.body()?.data}")
            } else {
                _responseMessage.postValue("${response.body()?.data}")
                Log.i(LOG_TAG, "${response.body()?.data}")
            }
        }
    }


    fun signIn(username: String, password: String) {
        val credential = SignIn(username, password)
        CoroutineScope(Dispatchers.IO).launch {
            val signinResponse = authenticationRepository.signinUser(credential)
            if (signinResponse.isSuccessful) {
                _response.postValue(signinResponse.body()!!.status)
                Log.i(LOG_TAG, "${signinResponse.body()?.data}")
            } else {
                Log.i(LOG_TAG, "${signinResponse.body()?.data}")
            }
        }
    }
}