package com.incwell.blackforest.ui.category


import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.incwell.blackforest.R
import com.incwell.blackforest.data.model.Category
import com.incwell.blackforest.ui.home.HomeCategoryRecyclerAdapter
import com.incwell.blackforest.ui.home.HomeViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class AllHomeCategoryFragment : Fragment(), HomeCategoryRecyclerAdapter.HomeCategoryItemListener {
    private val homeViewModel: HomeViewModel by sharedViewModel()
    private val categoryViewModel: CategoryViewModel by sharedViewModel()

    private lateinit var allCategoryRecyclerView: RecyclerView
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_all_category, container, false)
        allCategoryRecyclerView = root.findViewById(R.id.rv_allCategories)

        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)

        homeViewModel.categoryData.observe(this, Observer {
            val adapter = HomeCategoryRecyclerAdapter(requireContext(), false, it, this)
            allCategoryRecyclerView.adapter = adapter
        })
        return root
    }

    override fun onHomeCategoryItemClick(category: Category) {
        categoryViewModel.selectedCategory.value = category
        navController.navigate(R.id.action_categoryFragment_to_CategoryFragment)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.clear()
    }


}
