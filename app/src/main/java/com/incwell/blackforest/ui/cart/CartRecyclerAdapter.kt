package com.incwell.blackforest.ui.cart

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.incwell.blackforest.R
import com.incwell.blackforest.data.model.CartItem
import com.incwell.blackforest.data.model.UpdateItem
import com.incwell.blackforest.data.storage.SharedPref
import kotlinx.android.synthetic.main.cart_list_item.view.*
import kotlinx.coroutines.*

class CartRecyclerAdapter(
    private val context: Context,
    private val cartItem: ArrayList<CartItem>,
    private val cartViewModel: CartViewModel
) : RecyclerView.Adapter<CartRecyclerAdapter.ViewHolder>() {

    lateinit var mItemRemoveListener: ItemRemoveListener

    private lateinit var view: View
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        view = inflater.inflate(R.layout.cart_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = cartItem.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = cartItem[position]
        with(holder) {
            cartItemName?.let {
                it.text = product.name
            }
            cartItemPrice?.let {
                it.text = product.price
            }
            Glide.with(context)
                .load(product.main_image)
                .into(cartItemImage!!)
            qty.text = product.quantity.toString()

            qtyIncrease.setOnClickListener {
                runBlocking {
                    coroutineScope {
                        cartViewModel.updateCartItem(
                            UpdateItem(product.product_id, product.quantity+1),
                            product.id!!
                        )
                    }
                    if (cartViewModel.incDecStatus) {
                        qty.text = (qty.text.toString().toInt() + 1).toString()
                        Log.d("incDecQty", qty.text.toString())
                    }
                }
            }
            qtyDecrease.setOnClickListener {
                runBlocking {
                    coroutineScope {
                        cartViewModel.updateCartItem(
                            UpdateItem(product.product_id, product.quantity-1),
                            product.id!!
                        )
                    }
                    if (cartViewModel.incDecStatus && product.quantity >= 1) {
                        qty.text = (qty.text.toString().toInt() - 1).toString()
                        Log.d("incDecQty", qty.text.toString())
                    }
                }
            }
        }
//        holder.itemView.cart_view.setData(cartItem[position])
    }

    fun setItemClearListener(removeListener: ItemRemoveListener) {
        mItemRemoveListener = removeListener
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cartItemImage: ImageView? = itemView.findViewById(R.id.cartItemImage)
        val cartItemName: TextView? = itemView.findViewById(R.id.cartItemName)
        val cartItemPrice: TextView? = itemView.findViewById(R.id.cartItemPrice)
        val qtyIncrease: AppCompatImageView = itemView.findViewById(R.id.iv_plus)
        val qtyDecrease: AppCompatImageView = itemView.findViewById(R.id.iv_minus)
        var qty: TextView = itemView.findViewById(R.id.tv_display)
    }
}

interface ItemRemoveListener {
    fun onItemCleared()
}
