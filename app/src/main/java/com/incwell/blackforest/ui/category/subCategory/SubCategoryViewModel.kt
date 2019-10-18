package com.incwell.blackforest.ui.category.subCategory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.incwell.blackforest.data.model.Category
import com.incwell.blackforest.data.model.SubCategory
import com.incwell.blackforest.data.repository.CategoryRepository
import com.incwell.blackforest.util.NoInternetException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException

class SubCategoryViewModel(private val categoryRepository: CategoryRepository) : ViewModel() {

    val selectedCategory = MutableLiveData<Category>()

    private val _subCategory = MutableLiveData<List<SubCategory>>()
    val subCategory: LiveData<List<SubCategory>>
        get() = _subCategory

    fun retrieveSubCategories(categoryId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val res = categoryRepository.getSubCategories(categoryId).isSuccessful
                if (res) {
                    val serviceData =
                        categoryRepository.getSubCategories(categoryId).body()?.data ?: emptyList()
                    _subCategory.postValue(serviceData)
                }
            } catch (e: NoInternetException) {

            } catch (e: SocketTimeoutException) {

            }
        }
    }
}