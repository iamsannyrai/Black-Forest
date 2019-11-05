package com.incwell.blackforest.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.SearchView
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
import com.incwell.blackforest.data.storage.SharedPref
import com.incwell.blackforest.ui.category.subCategory.SubCategoryViewModel
import com.incwell.blackforest.ui.product.ProductActivity
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class HomeFragment : Fragment(), CategoryRecyclerAdapter.CategoryItemListener,
    FeaturedRecyclerAdapter.FeaturedItemListener {

    private val homeViewModel: HomeViewModel by sharedViewModel()
    private val subCategoryViewModel: SubCategoryViewModel by sharedViewModel()

    private lateinit var featuredRecyclerView: RecyclerView
    private lateinit var categoryRecyclerView: RecyclerView
    private lateinit var navController: NavController
    private lateinit var shimmerFrameLayout: ShimmerFrameLayout
    private lateinit var nestedScrollView: NestedScrollView
    private lateinit var seeAllCategoryCardView: MaterialCardView
    private lateinit var searchProduct: SearchView

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
        seeAllCategoryCardView = root.findViewById(R.id.category_viewmore)
        searchProduct = root.findViewById(R.id.searcview_product)

        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)

        searchProduct.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                val searchBundle = Bundle()
                searchBundle.putString("searchTag", query)
                navController.navigate(R.id.action_nav_home_to_searchresultFragment, searchBundle)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })

        seeAllCategoryCardView.setOnClickListener {
            navController.navigate(R.id.action_nav_home_to_categoryFragment)
        }

        homeViewModel.featuredData.observe(this, Observer {
            val adapter = FeaturedRecyclerAdapter(requireContext(), it, this)
            featuredRecyclerView.adapter = adapter
            stopShimmerAnimation()
        })

        homeViewModel.categoryData.observe(this, Observer {
            val adapter = CategoryRecyclerAdapter(requireContext(), true, it, this)
            categoryRecyclerView.adapter = adapter
            stopShimmerAnimation()
        })
        return root
    }

    private fun stopShimmerAnimation() {
        shimmerFrameLayout.stopShimmer()
        nestedScrollView.visibility = View.VISIBLE
        shimmerFrameLayout.visibility = View.GONE
    }

    override fun onPause() {
        super.onPause()
        shimmerFrameLayout.stopShimmer()
    }

    override fun onFeaturedItemClick(product: Product) {
        val intent = Intent(activity, ProductActivity::class.java)
        intent.putExtra("product",product)
        startActivity(intent)
    }

    override fun onCategoryItemClick(category: Category) {
        subCategoryViewModel.selectedCategory.value = category
        navController.navigate(R.id.action_nav_home_to_subCategoryFragment)
    }
}
