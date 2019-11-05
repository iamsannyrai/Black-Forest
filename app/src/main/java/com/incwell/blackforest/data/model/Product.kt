package com.incwell.blackforest.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Product(
    var is_feature: Boolean,
    var status: Boolean,
    val id: Int,
    val name: String,
    val sub_category: List<String>,
    val description: String,
    val main_image: String,
    val ingredeint: List<String>,
    val tags: List<String>,
    val price: String
):Parcelable