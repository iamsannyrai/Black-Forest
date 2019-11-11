package com.incwell.blackforest.ui.order

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.incwell.blackforest.R
import com.incwell.blackforest.data.model.CartItem

class OrderRecyclerAdapter(val context: Context, private val cartItems: List<CartItem>) :
    RecyclerView.Adapter<OrderRecyclerAdapter.ViewHolder>() {

    private lateinit var view: View

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        view = inflater.inflate(R.layout.order_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = cartItems.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cartItem = cartItems[position]
        with(holder) {
            orderProductName.text = cartItem.name
            orderProductQty.text = cartItem.quantity.toString()
            orderProductTotal.text = (cartItem.quantity * cartItem.price.toInt()).toString()
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val orderProductName: TextView = itemView.findViewById(R.id.order_product_name)
        val orderProductQty: TextView = itemView.findViewById(R.id.order_product_qty)
        val orderProductTotal: TextView = itemView.findViewById(R.id.order_product_total)
    }
}