package com.incwell.blackforest.ui.order

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import com.google.android.material.snackbar.Snackbar
import com.incwell.blackforest.R
import com.incwell.blackforest.TAX
import com.incwell.blackforest.data.model.NewAddress
import com.incwell.blackforest.data.storage.SharedPref
import com.incwell.blackforest.ui.home.FeaturedRecyclerAdapter
import com.incwell.blackforest.util.dropDown
import kotlinx.android.synthetic.main.activity_signup.*
import kotlinx.android.synthetic.main.fragment_order.*
import kotlinx.android.synthetic.main.fragment_order.view.*
import org.koin.android.ext.android.inject

class OrderFragment : Fragment() {

    private val orderViewModel: OrderViewModel by inject()
    private lateinit var myCity: HashMap<String, Int>
    private lateinit var orderRecyclerView: RecyclerView
    private lateinit var navController: NavController
    private var subtotal: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_order, container, false)
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
        orderRecyclerView = root.rv_order

        val adapter = OrderRecyclerAdapter(requireContext(), orderViewModel.cartItem)
        orderRecyclerView.adapter = adapter

        root.subTotal.text = getSubTotalAmount().toString()
        root.tax.text = calculateTax().toString()
        root.orderTotal.text = calculateTotal().toString()


        myCity = HashMap()
        for (i in 0 until SharedPref.getCity().size) {
            Log.d("cities1", "${SharedPref.getCity()[i]}")
            myCity[SharedPref.getCity()[i].city] = SharedPref.getCity()[i].id
        }
        dropDown(context!!, myCity.keys.toTypedArray(), root.other_city)

        val checkbox = root.otherLocation
        checkbox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                TransitionManager.beginDelayedTransition(root.cardView, AutoTransition())
                root.expandableCardView.visibility = View.VISIBLE
            } else {
                TransitionManager.beginDelayedTransition(root.cardView, AutoTransition())
                root.expandableCardView.visibility = View.GONE
            }
        }

        root.order.setOnClickListener { orderBtn ->
            Log.d("checkbox", "${checkbox.isChecked}")
            val newAddress = NewAddress(
                1,
                checkbox.isChecked,
                root.other_phone_number.text.toString(),
                "${myCity[root.other_city.text.toString()]!!}",
                root.other_address.text.toString()
            )
            orderViewModel.orderProduct(newAddress)
            orderViewModel.orderReqResponse.observe(this, Observer {
                if (it) {
                    Snackbar.make(
                        orderBtn,
                        "Your order is confirmed. Please check mail.",
                        Snackbar.LENGTH_SHORT
                    ).show()
                    SharedPref.deleteAllCartItem()
                    navController.navigate(R.id.action_orderFragment_to_nav_home)
                } else {
                    Snackbar.make(
                        orderBtn,
                        "Sorry your order couldnot be confirmed!",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }

            })
        }

        return root
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.clear()
    }

    private fun getSubTotalAmount(): Double {
        val items = SharedPref.getCart()
        for (i in 0 until items.size) {
            subtotal += (items[i].quantity * items[i].price.toInt()).toDouble()
        }
        return subtotal
    }

    private fun calculateTax(): Double {
        return TAX * subtotal
    }

    private fun calculateTotal(): Double {
        return subtotal + calculateTax()
    }
}
