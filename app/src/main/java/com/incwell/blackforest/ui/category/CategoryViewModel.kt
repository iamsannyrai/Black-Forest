package com.incwell.blackforest.ui.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.incwell.blackforest.data.model.Category
import com.incwell.blackforest.data.model.CategoryItem
import com.incwell.blackforest.data.model.Product
import com.incwell.blackforest.data.model.SubCategoryItem
import com.incwell.blackforest.data.repository.CategoryRepository
import com.incwell.blackforest.util.NoInternetException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException

class CategoryViewModel(private val categoryRepository: CategoryRepository) : ViewModel() {

    val selectedCategory = MutableLiveData<Category>()

    val selectedCategoryProduct = MutableLiveData<CategoryItem>()

    val selectedSubCategoryProduct = MutableLiveData<SubCategoryItem>()

    private val _categoryItem = MutableLiveData<List<CategoryItem>>()
    val categoryItem: LiveData<List<CategoryItem>>
        get() = _categoryItem

    private val _subCategoryItem = MutableLiveData<List<SubCategoryItem>>()
    val subCategoryItem: LiveData<List<SubCategoryItem>>
        get() = _subCategoryItem

    fun retrieveSubCategories(categoryId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val res = categoryRepository.getSubCategories(categoryId).isSuccessful
                if (res) {
                    val serviceData =
                        categoryRepository.getSubCategories(categoryId).body()?.data ?: emptyList()
                    _categoryItem.postValue(serviceData)
                }
            } catch (e: NoInternetException) {

            } catch (e: SocketTimeoutException) {

            }
        }
    }

    fun retrieveSubCategoryProducts(subCategoryId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val res = categoryRepository.getSubCategoryProducts(subCategoryId).isSuccessful
                if (res) {
                    val response =
                        categoryRepository.getSubCategoryProducts(subCategoryId).body()?.data
                            ?: emptyList()
                    _subCategoryItem.postValue(response)

                }
            } catch (e: NoInternetException) {

            } catch (e: SocketTimeoutException) {

            }
        }
    }
}