package com.incwell.blackforest.ui.account

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.incwell.blackforest.R
import com.incwell.blackforest.data.model.History

class OrderHistoryRecyclerAdapter(
    val context: Context,
    private val orderList: List<History>
) : RecyclerView.Adapter<OrderHistoryRecyclerAdapter.ViewHolder>() {
    private lateinit var view: View

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        view = inflater.inflate(R.layout.order_history_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = orderList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val orderHistory = orderList[position]
        with(holder) {

            for (i in orderHistory.product.indices) {
                productName?.let {
                    it.text = orderHistory.product[i].product
                }
                qty?.let {
                    it.text = orderHistory.product[i].quantity.toString()
                }
            }
            orderCity?.let {
                it.text = orderHistory.common.city
            }
            orderAddress?.let {
                it.text = orderHistory.common.address
            }
            subTotal?.let {
                it.text = "Sub Total: Rs. ${orderHistory.common.sub_total}"
            }
            tax?.let {
                it.text = "Tax: Rs. ${orderHistory.common.total_tax}"
            }
            grandTotal?.let {
                it.text = "Grand Total: Rs. ${orderHistory.common.grand_total}"
            }

        }
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