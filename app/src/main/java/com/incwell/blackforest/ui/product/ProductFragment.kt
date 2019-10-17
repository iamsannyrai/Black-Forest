package com.incwell.blackforest.ui.product


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.incwell.blackforest.LOG_TAG
import com.incwell.blackforest.databinding.FragmentProductBinding
import com.incwell.blackforest.ui.home.HomeViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ProductFragment : Fragment() {

    private val homeViewModel: HomeViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        homeViewModel.selectedProduct.observe(this, Observer {
            Log.i(LOG_TAG, "$it")
        })

        val productBinding = FragmentProductBinding.inflate(inflater, container, false)
        productBinding.lifecycleOwner = this
        productBinding.product = homeViewModel

        return productBinding.root
    }

}
