package com.incwell.blackforest

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.incwell.blackforest.data.model.SearchedProduct

class SearchRecyclerAdapter(
    val context: Context,
    val searchedProducts: List<SearchedProduct>,
    val itemListener: SearchItemListener
) : RecyclerView.Adapter<SearchRecyclerAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val view = inflater.inflate(R.layout.search_list_item, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int = searchedProducts.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val searchedProduct = searchedProducts[position]
        with(holder) {
            searchItemTitle.text = searchedProduct.name
            Glide.with(context)
                .load(searchedProduct.image)
                .into(searchItemImage)

            itemView.setOnClickListener {
                itemListener.onSearchItemClick(searchedProduct)
            }
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val searchItemImage: ImageView = itemView.findViewById(R.id.iv_searchedItem)
        val searchItemTitle: TextView = itemView.findViewById(R.id.tv_searchedItem)
    }

    interface SearchItemListener {
        fun onSearchItemClick(searchedProduct: SearchedProduct)
    }
}