package com.incwell.blackforest.ui.search


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.incwell.blackforest.LOG_TAG
import com.incwell.blackforest.R
import com.incwell.blackforest.data.model.SearchedProduct
import com.incwell.blackforest.ui.product.ProductActivity
import org.koin.android.ext.android.inject


class SearchFragment : Fragment(),
    SearchRecyclerAdapter.SearchItemListener,
    SuggestionRecyclerAdapter.SuggestionItemListener {
    private val searchViewModel: SearchViewModel by inject()

    private lateinit var searchedRecyclerView: RecyclerView

    private lateinit var suggestionRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_searchresult, container, false)
        searchedRecyclerView = root.findViewById(R.id.rv_searchResult)
        suggestionRecyclerView = root.findViewById(R.id.rv_suggestion)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val query = arguments?.getString("searchTag")
        searchViewModel.retrieveSearchedData(query!!)
        searchViewModel.searchedData.observe(this, Observer {
            for (i in it.indices) {
                val resultAdapter = SearchRecyclerAdapter(
                    requireContext(),
                    it[i].data_found,
                    this
                )
                searchedRecyclerView.adapter = resultAdapter

                val suggestionAdapter =
                    SuggestionRecyclerAdapter(
                        requireContext(),
                        it[i].products,
                        this
                    )
                suggestionRecyclerView.adapter = suggestionAdapter
            }
        })
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.clear()
    }

    override fun onSearchItemClick(searchedProduct: SearchedProduct) {
        val intent = Intent(activity, ProductActivity::class.java)
        intent.putExtra("productId", "${searchedProduct.id}")
        startActivity(intent)
    }

    override fun onSuggestionItemClick(suggestedProduct: SearchedProduct) {
        val intent = Intent(activity, ProductActivity::class.java)
        intent.putExtra("productId", "${suggestedProduct.id}")
        startActivity(intent)
    }
}
