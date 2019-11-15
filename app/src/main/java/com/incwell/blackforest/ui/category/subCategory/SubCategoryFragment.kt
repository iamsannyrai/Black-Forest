package com.incwell.blackforest.ui.category.subCategory


import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.incwell.blackforest.R
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SubCategoryFragment : Fragment() {
    private val subCategoryViewModel: SubCategoryViewModel by sharedViewModel()

    private lateinit var subCategoryRecyclerView: RecyclerView

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
        subCategoryRecyclerView = root.findViewById(R.id.rv_sub_categories)

        subCategoryViewModel.selectedCategory.observe(this, Observer {
            subCategoryViewModel.retrieveSubCategories(it.id)
            //sets title of fragment dynamically
            (requireActivity() as AppCompatActivity).run {
                supportActionBar?.title = it.name
            }
        })

        subCategoryViewModel.subCategory.observe(this, Observer {
            val subCategoryRecyclerAdapter =
                SubCategoryRecyclerAdapter(
                    requireContext(),
                    it
                )
            subCategoryRecyclerView.adapter = subCategoryRecyclerAdapter
        })

        return root
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.clear()
    }

}
