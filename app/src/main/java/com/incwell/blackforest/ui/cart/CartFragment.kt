package com.incwell.blackforest.ui.cart


import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import com.incwell.blackforest.LOG_TAG
import com.incwell.blackforest.R
import com.incwell.blackforest.data.model.CartItem
import com.incwell.blackforest.data.model.UpdateItem
import com.incwell.blackforest.data.storage.SharedPref
import com.incwell.blackforest.util.showSnackbar
import kotlinx.android.synthetic.main.fragment_cart.view.*
import org.koin.android.ext.android.inject
import java.lang.IndexOutOfBoundsException

class CartFragment : Fragment() {

    private val cartViewModel: CartViewModel by inject()

    private lateinit var navController: NavController
    private lateinit var trashIcon: Drawable
    private lateinit var adapter: CartRecyclerAdapter

    private var totalAmount: Double = 0.0
    private var cartItems: ArrayList<CartItem> = ArrayList()

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

        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)

        //checkout whether data is empty or not
        cartViewModel.getCartItem()
        cartViewModel.cartData.observe(this, Observer {
            cartItems.clear()
            for (element in it) {
                cartItems.add(element)
            }
            if (it.isEmpty()) {
                root.iv_empty_cart.visibility = View.VISIBLE
                root.checkout.visibility = View.GONE
            } else {
                root.iv_empty_cart.visibility = View.GONE
                root.checkout.visibility = View.VISIBLE

                adapter = CartRecyclerAdapter(requireContext(), cartItems,cartViewModel)
                root.rv_cart.adapter = adapter

                adapter.setItemClearListener(object : ItemRemoveListener {
                    override fun onItemCleared() {
                        root.iv_empty_cart.visibility = View.VISIBLE
                        root.checkout.visibility = View.GONE
                    }
                })
            }
            Log.d("size", "size ${cartItems.size}")
        })

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
                Log.d("size", "Before index $pos, size ${cartItems.size}")
                if (direction == ItemTouchHelper.LEFT) {
                    cartViewModel.removeItemFromCart(cartItems[pos].id!!)
                    cartViewModel.removeFromCartResponse.observe(activity!!, Observer {
                        try {
                            when (it) {
                                true -> {
                                    cartItems.removeAt(pos)
                                    adapter.notifyItemRemoved(pos)
                                    if (cartItems.isEmpty()) {
                                        adapter.mItemRemoveListener.onItemCleared()
                                    }
                                    context!!.showSnackbar(view!!, "Item removed successfully!")
                                }
                                false -> {
                                    context!!.showSnackbar(
                                        view!!,
                                        "Something went wrong while removing item from cart. Please try again later"
                                    )
                                }
                            }
                        }catch (e:IndexOutOfBoundsException){

                        }
                        Log.d("size", "After index $pos, size ${cartItems.size}")
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


        root.checkout.setOnClickListener {
            Toast.makeText(context, "$totalAmount", Toast.LENGTH_LONG).show()
            navController.navigate(R.id.action_cartFragment_to_orderFragment)
        }
        return root
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.clear()
    }

//    private fun getTotalAmount() {
//        val items = cartItems
//        for (i in items.indices) {
//            totalAmount += items[i].quantity * items[i].price.toDouble()
//        }
//        Log.d("total", "$totalAmount")
//    }
}



