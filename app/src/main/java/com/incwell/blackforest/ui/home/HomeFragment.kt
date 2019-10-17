package com.incwell.blackforest.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.card.MaterialCardView
import com.incwell.blackforest.R
import com.incwell.blackforest.data.model.Category
import com.incwell.blackforest.data.model.Product
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class HomeFragment : Fragment(), CategoryRecyclerAdapter.CategoryItemListener,
    FeaturedRecyclerAdapter.FeaturedItemListener {

    private val homeViewModel: HomeViewModel by sharedViewModel()

    private lateinit var featuredRecyclerView: RecyclerView
    private lateinit var categoryRecyclerView: RecyclerView
    private lateinit var navController: NavController
    private lateinit var shimmerFrameLayout: ShimmerFrameLayout
    private lateinit var nestedScrollView: NestedScrollView



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        categoryRecyclerView = root.findViewById(R.id.rv_categories)
        featuredRecyclerView = root.findViewById(R.id.rv_featured)
        shimmerFrameLayout = root.findViewById(R.id.shimmer_layout)
        nestedScrollView = root.findViewById(R.id.nested_scroll_view)


        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)

        homeViewModel.featuredData.observe(this, Observer {
            val adapter = FeaturedRecyclerAdapter(requireContext(), it, this)
            featuredRecyclerView.adapter = adapter
            displayData()
        })

        homeViewModel.categoryData.observe(this, Observer {
            val adapter = CategoryRecyclerAdapter(requireContext(), it, this)
            categoryRecyclerView.adapter = adapter
            displayData()
        })

        return root
    }

    private fun displayData(){
        shimmerFrameLayout.stopShimmer()
        nestedScrollView.visibility = View.VISIBLE
        shimmerFrameLayout.visibility = View.GONE
    }

    override fun onResume() {
        super.onResume()
        shimmerFrameLayout.startShimmer()
    }

    override fun onPause() {
        super.onPause()
        shimmerFrameLayout.stopShimmer()
    }

    override fun onFeaturedItemClick(product: Product) {
        homeViewModel.selectedProduct.value = product
        navController.navigate(R.id.action_nav_home_to_productFragment)
    }

    override fun onCategoryItemClick(category: Category) {
        homeViewModel.selectedCategory.value = category
        navController.navigate(R.id.action_nav_home_to_categoryFragment)
    }
}
