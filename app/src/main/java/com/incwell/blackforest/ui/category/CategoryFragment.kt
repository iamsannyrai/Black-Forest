package com.incwell.blackforest.ui.category


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.incwell.blackforest.R
import com.incwell.blackforest.data.model.CategoryItem
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class CategoryFragment : Fragment(),CategoryRecyclerAdapter.SubCategoryItemListener{


    private val categoryViewModel: CategoryViewModel by sharedViewModel()

    private lateinit var categoryRecyclerView: RecyclerView
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
        val root = inflater.inflate(R.layout.fragment_category, container, false)
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
        categoryRecyclerView = root.findViewById(R.id.rv_categories)

        categoryViewModel.selectedCategory.observe(this, Observer {
            categoryViewModel.retrieveSubCategories(it.id)
            //sets title of fragment dynamically
            (requireActivity() as AppCompatActivity).run {
                supportActionBar?.title = it.name
            }
        })

        categoryViewModel.categoryItem.observe(this, Observer {
            val categoryRecyclerAdapter =
                CategoryRecyclerAdapter(
                    requireContext(), it,this
                )
            categoryRecyclerView.adapter = categoryRecyclerAdapter
        })

        return root
    }

    override fun onSubCategoryItemClick(categoryItem: CategoryItem) {
        categoryViewModel.selectedCategoryProduct.value = categoryItem
        navController.navigate(R.id.action_categoryFragment_to_subCategoryFragment)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.clear()
    }


}
