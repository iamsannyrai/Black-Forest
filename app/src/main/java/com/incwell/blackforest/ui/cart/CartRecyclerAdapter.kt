package com.incwell.blackforest.ui.cart

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.incwell.blackforest.R
import com.incwell.blackforest.data.model.CartItem
import com.incwell.blackforest.data.storage.SharedPref
import kotlinx.android.synthetic.main.cart_list_item.view.*

class CartRecyclerAdapter(
    private val context: Context,
    private val cartItem: ArrayList<CartItem>
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
            cartItemTotalPrice?.let {
                it.text=""
                it.text =
                    (product.price.toInt() * holder.itemView.cart_view.getQuantity(cartItem[position])).toString()
            }
        }
        holder.itemView.cart_view.setData(cartItem[position])
    }
    fun setItemClearListener(removeListener: ItemRemoveListener) {
        mItemRemoveListener = removeListener
    }

    fun removeItem(position: Int) {
        cartItem.removeAt(position)
        notifyItemRemoved(position)
        SharedPref.saveCartItems(cartItem)
        if (cartItem.size == 0) {
            mItemRemoveListener.onItemCleared()
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cartItemImage: ImageView? = itemView.findViewById(R.id.cartItemImage)
        val cartItemName: TextView? = itemView.findViewById(R.id.cartItemName)
        val cartItemPrice: TextView? = itemView.findViewById(R.id.cartItemPrice)
        val cartItemTotalPrice: TextView? = itemView.findViewById(R.id.itemTotalPrice)
    }
}

interface ItemRemoveListener {
    fun onItemCleared()
}
