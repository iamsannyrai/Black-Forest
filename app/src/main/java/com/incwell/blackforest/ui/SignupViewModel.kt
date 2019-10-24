package com.incwell.blackforest.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.incwell.blackforest.LOG_TAG
import com.incwell.blackforest.data.model.User
import com.incwell.blackforest.data.repository.SignupRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignupViewModel(private val signupRepository: SignupRepository) : ViewModel() {

    private val _registrationResponse = MutableLiveData<Boolean>()
    val registrationResponse: LiveData<Boolean>
        get() = _registrationResponse

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
            val response = signupRepository.registerUser(newUser)
            if (response.isSuccessful) {
                _registrationResponse.postValue(response.body()?.status)
                Log.i(LOG_TAG, "${response.body()?.data}")
            } else {
                _responseMessage.postValue("${response.body()?.data}")
                Log.i(LOG_TAG, "${response.body()?.data}")
            }
        }
    }
}