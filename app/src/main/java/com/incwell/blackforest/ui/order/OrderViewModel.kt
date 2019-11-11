package com.incwell.blackforest.ui.order

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.incwell.blackforest.data.model.NewAddress
import com.incwell.blackforest.data.repository.OrderRepository
import com.incwell.blackforest.util.NoInternetException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException

class OrderViewModel(private val orderRepository: OrderRepository) : ViewModel() {
    val cartItem = orderRepository.getCartItemsFromLocalStorage()

    private val _orderReqResponse = MutableLiveData<Boolean>()
    val orderReqResponse: LiveData<Boolean>
        get() = _orderReqResponse

    fun orderProduct(newAddress: NewAddress){
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val res = orderRepository.orderProduct(newAddress)
                if (res.isSuccessful) {
                    _orderReqResponse.postValue(true)
                }else{
                    _orderReqResponse.postValue(false)
                }
            } catch (e: NoInternetException) {

            } catch (e: SocketTimeoutException) {

            }
        }
    }

}