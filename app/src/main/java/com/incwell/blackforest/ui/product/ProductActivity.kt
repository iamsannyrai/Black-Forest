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
import com.incwell.blackforest.ui.category.CategoryViewModel
import kotlinx.android.synthetic.main.activity_product.*
import org.koin.android.ext.android.inject

class ProductActivity : AppCompatActivity() {

    private val productViewModel: ProductViewModel by inject()
    private val cartViewModel: CartViewModel by inject()

    lateinit var product: Product

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)

        val id = intent.getStringExtra("productId")!!.toInt()
        productViewModel.getProductDetail(id)

        productViewModel.product.observe(this, Observer {
            product = it
            val adapter = IngredientRecyclerAdapter(it.ingredeint)
            rv_ingredient.adapter = adapter
            product_name.text = it.name
            product_price.text = "Rs ${it.price}"
            product_description.text = it.description
            Glide.with(this).load(it.main_image).into(product_image)
        })

        fab_back.setOnClickListener {
            //works as if it is backpresed
            onBackPressed()
        }

        add_to_cart_btn.setOnClickListener { view ->
            cartViewModel.addToCartResult(product.id.toString())
            cartViewModel.cartResult.observe(this, Observer {
                Snackbar.make(view, it, Snackbar.LENGTH_SHORT).show()
                SharedPref.addToCart(product)
            })
        }
    }
}
