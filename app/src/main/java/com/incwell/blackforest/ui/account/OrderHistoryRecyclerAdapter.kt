package com.incwell.blackforest.ui.account

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.incwell.blackforest.R

class OrderHistoryRecyclerAdapter() : RecyclerView.Adapter<OrderHistoryRecyclerAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemCount(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productName: TextView? = itemView.findViewById(R.id.orderHistory_productName)
        val qty: TextView? = itemView.findViewById(R.id.orderHistory_productQty)
        val phoneNum: TextView? = itemView.findViewById(R.id.orderHistory_phoneNumber)
        val orderCity: TextView? = itemView.findViewById(R.id.orderHistory_city)
        val orderAddress: TextView? = itemView.findViewById(R.id.orderHistory_address)
        val subTotal: TextView? = itemView.findViewById(R.id.orderHistory_subTotal)
        val tax: TextView? = itemView.findViewById(R.id.orderHistory_tax)
        val grandTotal: TextView? = itemView.findViewById(R.id.orderHistory_grandTotal)
    }
}