package com.incwell.blackforest.ui.category.subCategory

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.incwell.blackforest.R
import com.incwell.blackforest.data.model.Product
import com.incwell.blackforest.data.model.SubCategoryItem
import com.incwell.blackforest.ui.product.ProductActivity
import com.incwell.blackforest.ui.search.SearchRecyclerAdapter

class SubCategoryRecyclerAdapter(
    val context: Context,
    private val subCategoryItems: List<SubCategoryItem>,
    val itemListener: SubCategoryItemProductListener
) : RecyclerView.Adapter<SubCategoryRecyclerAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.subcategory_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = subCategoryItems.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val subCategoryProduct = subCategoryItems[position]
        with(holder) {
            subCategoryProductName?.let {
                it.text = subCategoryProduct.name
            }
            Glide.with(context)
                .load(subCategoryProduct.image)
                .into(subCategoryProductImage!!)

            itemView.setOnClickListener {
                itemListener.onSubCategorytemProductClick(subCategoryProduct)
            }
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val subCategoryProductName: TextView? = itemView.findViewById(R.id.tv_subCategoryName)
        val subCategoryProductImage: ImageView? = itemView.findViewById(R.id.imgv_subCategoryImage)
    }

    interface SubCategoryItemProductListener {
        fun onSubCategorytemProductClick(subCategoryItem: SubCategoryItem)
    }
}