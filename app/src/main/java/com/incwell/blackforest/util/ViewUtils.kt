package com.incwell.blackforest.util

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.incwell.blackforest.R

fun hideErrorHint(textInputEditText: TextInputEditText, textInputLayout: TextInputLayout){
    textInputEditText.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {

        }
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            textInputLayout.error = null
        }
    })
}

fun dropDown(context: Context, objects: Array<String>, id: AutoCompleteTextView) {
    id.setAdapter(ArrayAdapter(context, R.layout.dropdown_menu_pop_item, objects))
}



