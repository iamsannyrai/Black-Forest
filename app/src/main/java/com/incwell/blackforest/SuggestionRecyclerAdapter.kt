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

class SuggestionRecyclerAdapter(
    val context: Context,
    val suggestedProducts: List<SearchedProduct>,
    val itemListener: SuggestionItemListener
) : RecyclerView.Adapter<SuggestionRecyclerAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val view = inflater.inflate(R.layout.suggestion_list_item, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int = suggestedProducts.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val suggestedProduct = suggestedProducts[position]
        with(holder) {
            suggestionItemTitle.text = suggestedProduct.name
            Glide.with(context)
                .load(suggestedProduct.image)
                .into(suggestionItemImage)

            itemView.setOnClickListener {
                itemListener.onSuggestionItemClick(suggestedProduct)
            }
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val suggestionItemImage: ImageView = itemView.findViewById(R.id.iv_suggestionItem)
        val suggestionItemTitle: TextView = itemView.findViewById(R.id.tv_suggestionItem)
    }

    interface SuggestionItemListener {
        fun onSuggestionItemClick(suggestedProduct: SearchedProduct)
    }
}