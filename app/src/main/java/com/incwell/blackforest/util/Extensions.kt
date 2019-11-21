package com.incwell.blackforest.util

import android.content.Context
import android.view.View
import com.google.android.material.snackbar.Snackbar

fun Context.showSnackbar(view:View,message:String){
    Snackbar.make(view,message,Snackbar.LENGTH_SHORT).show()
}