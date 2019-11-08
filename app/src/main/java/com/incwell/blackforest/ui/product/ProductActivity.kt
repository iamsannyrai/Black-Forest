package com.incwell.blackforest.ui.product

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.incwell.blackforest.R
import com.incwell.blackforest.data.model.Product
import com.incwell.blackforest.data.storage.SharedPref
import com.incwell.blackforest.ui.cart.CartViewModel
import kotlinx.android.synthetic.main.activity_product.*
import org.koin.android.ext.android.inject

class ProductActivity : AppCompatActivity() {

    val cartViewModel:CartViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)

        val product = intent.getParcelableExtra<Product>("product")
        product_name.text = product!!.name
        product_description.text = product.description
        Glide.with(this).load(product.main_image).into(product_image)

        fab_back.setOnClickListener {
            //works as if it is backpresed
            onBackPressed()
        }

        add_to_cart_btn.setOnClickListener {view->
            cartViewModel.addToCartResult(product.id.toString())
            cartViewModel.cartResult.observe(this, Observer {
                Snackbar.make(view,it,Snackbar.LENGTH_SHORT).show()
                SharedPref.addToCart(product)
            })
        }
    }
}
