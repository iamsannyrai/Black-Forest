package com.incwell.blackforest.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.incwell.blackforest.LOG_TAG
import com.incwell.blackforest.R
import com.incwell.blackforest.data.Category
import com.incwell.blackforest.data.Product

class HomeFragment : Fragment(), CategoryRecyclerAdapter.CategoryItemListener, FeaturedRecyclerAdapter.FeaturedItemListener {
    private lateinit var homeViewModel: HomeViewModel

    private lateinit var featuredRecyclerView: RecyclerView

    private lateinit var categoryRecyclerView: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        categoryRecyclerView = root.findViewById(R.id.rv_categories)
        featuredRecyclerView = root.findViewById(R.id.rv_featured)

        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)

        homeViewModel.featuredData.observe(this, Observer {
            val adapter = FeaturedRecyclerAdapter(requireContext(),it,this)
            featuredRecyclerView.adapter = adapter
        })

        homeViewModel.categoryData.observe(this, Observer {
            val adapter = CategoryRecyclerAdapter(requireContext(), it, this)
            categoryRecyclerView.adapter = adapter
        })

        return root
    }

    override fun onFeaturedItemClick(product: Product) {
        Log.i(LOG_TAG, "${product.name} :clicked")
    }

    override fun onCategoryItemClick(category: Category) {
        Log.i(LOG_TAG, "${category.name} :clicked")
    }
}
