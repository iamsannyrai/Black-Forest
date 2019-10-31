package com.incwell.blackforest.data.model

class BaseResponse<T> {
    var data: T? = null
    var status: Boolean ?= null
}