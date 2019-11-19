package com.incwell.blackforest.ui.account

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.incwell.blackforest.data.model.*
import com.incwell.blackforest.data.repository.AccountRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AccountViewModel(private val accountRepository: AccountRepository) : ViewModel() {

    private val _profile = MutableLiveData<Profile>()
    val profile: LiveData<Profile>
        get() = _profile

    private val _status = MutableLiveData<Boolean>()
    val status: LiveData<Boolean>
        get() = _status

    private val _address = MutableLiveData<CompleteProfile>()
    val address: LiveData<CompleteProfile>
        get() = _address

    private val _orderHistory = MutableLiveData<List<History>?>()
    val orderHistory: LiveData<List<History>?>
        get() = _orderHistory

    fun getProfile() {
        CoroutineScope(Dispatchers.IO).launch {
            val response = accountRepository.getProfile()
            if (response.isSuccessful) {
                _profile.postValue(response.body()!!.data)
            } else {
                Log.d("profile", "$response")
            }
        }
    }

    fun changePhoneNumber(phoneNumber: PhoneNumber) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = accountRepository.changePhonenumber(phoneNumber)
            if (response.isSuccessful) {
                _status.postValue(true)
            } else {
                _status.postValue(false)
            }
        }
    }

    fun changePassword(password: NewPassword) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = accountRepository.changePassword(password)
            if (response.isSuccessful) {
                _status.postValue(true)
            } else {
                _status.postValue(false)
            }
        }
    }

    fun getAddress() {
        CoroutineScope(Dispatchers.IO).launch {
            val response = accountRepository.getAddress()
            if (response.isSuccessful) {
                _address.postValue(response.body()!!.data)
            } else {
                Log.d("completeprofile", "$response")
            }
        }
    }

    fun changeCity(city: NewCity) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = accountRepository.changeCity(city)
            if (response.isSuccessful) {
                _status.postValue(true)
            } else {
                _status.postValue(false)
            }
        }
    }

    fun changeAddress(address: Address) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = accountRepository.changeAddress(address)
            if (response.isSuccessful) {
                _status.postValue(true)
            } else {
                _status.postValue(false)
            }
        }
    }

    fun getHistory() {
        CoroutineScope(Dispatchers.IO).launch {
            val response = accountRepository.getOrderHistory()
            if (response.isSuccessful) {
                _orderHistory.postValue(response.body()!!.data)
                Log.d("order-history", "${response.body()!!.data}")
            } else {
                Log.d("order-history", "${response}")
            }
        }
    }
}