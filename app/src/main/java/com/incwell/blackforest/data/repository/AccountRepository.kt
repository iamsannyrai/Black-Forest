package com.incwell.blackforest.data.repository

import com.incwell.blackforest.data.BlackForestService
import com.incwell.blackforest.data.model.Address
import com.incwell.blackforest.data.model.NewCity
import com.incwell.blackforest.data.model.NewPassword
import com.incwell.blackforest.data.model.PhoneNumber

class AccountRepository(private val blackForestService: BlackForestService) {
    suspend fun getProfile() = blackForestService.getProfile()

    suspend fun changePhonenumber(phonenumber: PhoneNumber) =
        blackForestService.changePhoneNumber(phonenumber)

    suspend fun changePassword(password: NewPassword) = blackForestService.changePassword(password)

    suspend fun getAddress() = blackForestService.getAddress()

    suspend fun changeCity(newCity: NewCity)=blackForestService.changeCity(newCity)

    suspend fun changeAddress(newAddress: Address)=blackForestService.changeAddress(newAddress)

}