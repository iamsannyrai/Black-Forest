package com.incwell.blackforest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.incwell.blackforest.data.model.Search
import com.incwell.blackforest.data.repository.SearchRepository
import com.incwell.blackforest.util.NoInternetException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException

class SearchViewModel(private val searchRepository: SearchRepository) : ViewModel() {

    private val _searchedData = MutableLiveData<List<Search>>()
    val searchedData: LiveData<List<Search>>
        get() = _searchedData

    fun retrieveSearchedData(query: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val res = searchRepository.retrieveSearchedProduct(query).isSuccessful
                if (res) {
                    val resultData =
                        searchRepository.retrieveSearchedProduct(query).body()?.data ?: emptyList()
                    _searchedData.postValue(resultData)
                }

            } catch (e: NoInternetException) {

            } catch (e: SocketTimeoutException) {

            }
        }
    }
}