package com.incwell.blackforest.data.model

data class NewPassword(
    val old_password: String,
    val new_password: String,
    val confirm_password: String
)