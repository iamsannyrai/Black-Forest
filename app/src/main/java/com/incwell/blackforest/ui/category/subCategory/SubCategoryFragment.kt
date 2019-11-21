package com.incwell.blackforest.ui.category.subCategory


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.incwell.blackforest.LOG_TAG
import com.incwell.blackforest.R
import com.incwell.blackforest.data.model.SubCategoryItem
import com.incwell.blackforest.ui.category.CategoryViewModel
import com.incwell.blackforest.ui.product.ProductActivity
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class SubCategoryFragment : Fragment(), SubCategoryRecyclerAdapter.SubCategoryItemProductListener {

    private val categoryViewModel: CategoryViewModel by sharedViewModel()
    private lateinit var subCategoryRecyclerView: RecyclerView
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
        val root = inflater.inflate(R.layout.fragment_subcategory, container, false)
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
        subCategoryRecyclerView = root.findViewById(R.id.rv_sub_categories)

        categoryViewModel.selectedCategoryProduct.observe(this, Observer {
            categoryViewModel.retrieveSubCategoryProducts(it.id)
            //sets title of fragment dynamically
            (requireActivity() as AppCompatActivity).run {
                supportActionBar?.title = it.sub_category
            }
        })

        categoryViewModel.subCategoryItem.observe(this, Observer {
            for (i in it.indices) {
                val subCategoryRecyclerAdapter = SubCategoryRecyclerAdapter(
                    requireContext(), it, this
                )
                subCategoryRecyclerView.adapter = subCategoryRecyclerAdapter
            }
        })
        return root
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.clear()
    }

    override fun onSubCategorytemProductClick(subCategoryItem: SubCategoryItem) {
        val intent = Intent(activity, ProductActivity::class.java)
        intent.putExtra("productId", "${subCategoryItem.id}")
        startActivity(intent)
    }
}
