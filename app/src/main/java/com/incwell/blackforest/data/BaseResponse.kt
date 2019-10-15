package com.incwell.blackforest.data

class BaseResponse<T> {
    var data: T? = null
    var status: Boolean = false
}