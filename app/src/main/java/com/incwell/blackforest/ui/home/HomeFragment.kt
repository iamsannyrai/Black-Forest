package com.incwell.blackforest.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.material.snackbar.Snackbar
import com.incwell.blackforest.R
import com.incwell.blackforest.data.model.Category
import com.incwell.blackforest.data.model.Product
import com.incwell.blackforest.ui.cart.CartViewModel
import com.incwell.blackforest.ui.category.CategoryViewModel
import com.incwell.blackforest.ui.product.ProductActivity
import com.incwell.blackforest.util.showSnackbar
import kotlinx.android.synthetic.main.fragment_home.view.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class HomeFragment : Fragment(), HomeCategoryRecyclerAdapter.HomeCategoryItemListener,
    FeaturedRecyclerAdapter.FeaturedItemListener {

    private val homeViewModel: HomeViewModel by sharedViewModel()
    private val categoryViewModel: CategoryViewModel by sharedViewModel()
    private val cartViewModel: CartViewModel by inject()

    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root: View = inflater.inflate(R.layout.fragment_home, container, false)

        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)

        root.searchview_product.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                val searchBundle = Bundle()
                searchBundle.putString("searchTag", query)
                navController.navigate(R.id.action_nav_home_to_searchresultFragment, searchBundle)
                root.searchview_product.setQuery("", false)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })

        root.category_viewmore.setOnClickListener {
            navController.navigate(R.id.action_nav_home_to_allCategoryFragment)
        }

        homeViewModel.featuredData.observe(this, Observer {
            val adapter = FeaturedRecyclerAdapter(requireContext(), it, this)
            root.rv_featured.adapter = adapter
            stopShimmerAnimation(root)
        })

        homeViewModel.categoryData.observe(this, Observer {
            val adapter = HomeCategoryRecyclerAdapter(requireContext(), true, it, this)
            root.rv_categories.adapter = adapter
            stopShimmerAnimation(root)
        })

        homeViewModel.errorMessage.observe(this, Observer {
            if (it.isNotEmpty()) {
                context!!.showSnackbar(view!!, it)
            }
        })

        return root
    }

    override fun onFeaturedItemClick(product: Product) {
        val intent = Intent(activity, ProductActivity::class.java)
        intent.putExtra("productId", "${product.id}")
        startActivity(intent)
    }

    override fun onHomeCategoryItemClick(category: Category) {
        categoryViewModel.selectedCategory.value = category
        navController.navigate(R.id.action_nav_home_to_categoryFragment)
    }

    private fun stopShimmerAnimation(root: View) {
        root.shimmer_layout.stopShimmer()
        root.nested_scroll_view.visibility = View.VISIBLE
        root.shimmer_layout.visibility = View.GONE
    }
}
