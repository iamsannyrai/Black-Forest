package com.incwell.blackforest.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("productImage")
fun loadImageOfProduct(view:ImageView,imageUrl:String){
    Glide.with(view.context)
        .load(imageUrl)
        .into(view)
}