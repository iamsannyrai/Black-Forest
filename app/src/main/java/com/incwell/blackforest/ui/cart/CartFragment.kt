package com.incwell.blackforest.ui.cart


import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import com.incwell.blackforest.LOG_TAG
import com.incwell.blackforest.R
import com.incwell.blackforest.data.model.CartItem
import com.incwell.blackforest.data.storage.SharedPref
import kotlinx.android.synthetic.main.fragment_cart.*
import kotlinx.android.synthetic.main.fragment_cart.view.*
import org.koin.android.ext.android.inject

class CartFragment : Fragment() {

    private val cartViewModel: CartViewModel by inject()

    private lateinit var emptyCart: ImageView
    private lateinit var checkoutBtn: MaterialButton

    private lateinit var adapter: CartRecyclerAdapter
    private lateinit var trashIcon: Drawable
    private var totalAmount: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        trashIcon = ContextCompat.getDrawable(context!!, R.drawable.ic_delete)!!
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_cart, container, false)

        emptyCart = root.iv_empty_cart
        checkoutBtn = root.checkout

        adapter = CartRecyclerAdapter(requireContext(), SharedPref.getCart())


        //checkout whether data is empty or not
        val cartItems = SharedPref.getCart()
        showEmptyView(cartItems)
        adapter.setItemClearListener(object : ItemRemoveListener {
            override fun onItemCleared() {
                showEmptyView(ArrayList())
            }
        })

        root.rv_cart.adapter = adapter

        //for swiping to remove item from cart
        val simpleCallback = object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val pos = viewHolder.adapterPosition
                if (direction == ItemTouchHelper.LEFT) {
                    Log.d(LOG_TAG, "Swiped left product at pos $pos id is : ${cartItems[pos].id}")
                    //function to remove item from cart
                    cartViewModel.removeItemFromCart(cartItems[pos].id)
                    cartViewModel.cartResult.observe(activity!!, Observer {
                        when (it) {
                            "removed" -> {
                                adapter.removeItem(pos)
                                Snackbar.make(
                                    view!!,
                                    "Item removed successfully!",
                                    Snackbar.LENGTH_SHORT
                                ).show()
                            }
                        }
                    })
                }
            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                val itemView = viewHolder.itemView
                val iconMargin = (itemView.height - trashIcon.intrinsicHeight) / 2
                if (dX < 0) {
                    trashIcon.setBounds(
                        itemView.right - iconMargin - trashIcon.intrinsicWidth,
                        itemView.top + iconMargin,
                        itemView.right - iconMargin,
                        itemView.bottom - iconMargin
                    )
                    c.clipRect(
                        itemView.right + dX.toInt(),
                        itemView.top,
                        itemView.right,
                        itemView.bottom
                    )
                    trashIcon.draw(c)
                }
                super.onChildDraw(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
            }
        }

        val itemTouchHelper = ItemTouchHelper(simpleCallback)
        itemTouchHelper.attachToRecyclerView(root.rv_cart)

        getTotalAmount()

        root.checkout.text = "Total amount Rs $totalAmount"

        root.checkout.setOnClickListener {
            Toast.makeText(context, "$totalAmount", Toast.LENGTH_LONG).show()
        }


        return root
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.clear()
    }

    private fun getTotalAmount() {
        val items = SharedPref.getCart()
        for (i in 0 until items.size) {
            totalAmount += items[i].quantity * items[i].price.toDouble()
        }
        Log.d("total", "$totalAmount")
    }

    private fun showEmptyView(cartItems: ArrayList<CartItem>) {
        emptyCart.visibility = if (cartItems.isEmpty()) View.VISIBLE else View.GONE
        checkoutBtn.visibility = if (cartItems.isNotEmpty()) View.VISIBLE else View.GONE
    }
}



