package com.incwell.blackforest.ui.cart


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.incwell.blackforest.R
import com.incwell.blackforest.data.storage.SharedPref
import com.incwell.blackforest.ui.home.FeaturedRecyclerAdapter
import kotlinx.android.synthetic.main.fragment_cart.view.*
import org.koin.android.ext.android.inject

class CartFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_cart, container, false)

        val adapter = CartRecyclerAdapter(requireContext(), SharedPref.getCart())
        root.rv_cart.adapter = adapter

        root.rv_cart
        return root
    }
}
