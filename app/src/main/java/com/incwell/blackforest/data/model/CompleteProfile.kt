package com.incwell.blackforest.data.model

data class CompleteProfile(
    val google_id: String? = null,
    val email: String,
    val facebook_id: String? = null,
    val phone: String,
    var is_admin: Boolean,
    val city: String,
    var is_active: Boolean,
    var is_staff: Boolean,
    var is_verified: Boolean,
    val date_joined: String,
    val address: String,
    val username: String,
    val full_name: String
)