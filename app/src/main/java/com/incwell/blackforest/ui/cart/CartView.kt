package com.incwell.blackforest.ui.cart

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.incwell.blackforest.R
import com.incwell.blackforest.data.model.CartItem
import kotlinx.android.synthetic.main.cart_inc_dec.view.*


class CartView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    LinearLayout(context, attrs, defStyleAttr) {
    private lateinit var mCartItem: CartItem

    init {
        init(context)
    }

    private fun init(context: Context) {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.cart_inc_dec, this, true)

        iv_plus.setOnClickListener {
            tv_display.text = (tv_display.text.toString().toInt() + 1).toString()
            mCartItem.quantity = tv_display.text.toString().toInt()
        }

        iv_minus.setOnClickListener {
            if (tv_display.text.toString().toInt() > 1) {
                tv_display.text = (tv_display.text.toString().toInt() - 1).toString()
                mCartItem.quantity = tv_display.text.toString().toInt()
            }
        }
    }

    fun setData(cartItem: CartItem) {
        mCartItem = cartItem
        tv_display.text = mCartItem.quantity.toString()
    }
}
