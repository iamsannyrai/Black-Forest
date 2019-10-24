package com.incwell.blackforest.data.model

data class User(
    var username: String,
    var first_name: String,
    var last_name: String,
    var email: String,
    var password: String,
    var city: Int,
    var address: String,
    var phone: String
)