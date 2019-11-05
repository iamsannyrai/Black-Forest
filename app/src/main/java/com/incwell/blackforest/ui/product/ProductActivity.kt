package com.incwell.blackforest.ui.product

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.incwell.blackforest.LOG_TAG
import com.incwell.blackforest.R
import com.incwell.blackforest.data.model.Product
import com.incwell.blackforest.data.storage.SharedPref
import com.incwell.blackforest.ui.home.HomeViewModel
import kotlinx.android.synthetic.main.activity_product.*
import org.koin.android.ext.android.inject

class ProductActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)

        val product = intent.getParcelableExtra<Product>("product")
        product_name.text = product!!.name
        product_description.text = product.description
        Glide.with(this).load(product.main_image).into(product_image)

        add_to_cart_btn.setOnClickListener {
            SharedPref.addToCart(product)
        }
    }
}
