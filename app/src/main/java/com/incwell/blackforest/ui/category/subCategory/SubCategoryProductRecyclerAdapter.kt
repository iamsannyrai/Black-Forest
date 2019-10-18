package com.incwell.blackforest.ui.category.subCategory

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.incwell.blackforest.R
import com.incwell.blackforest.data.model.Product

class SubCategoryProductRecyclerAdapter(
    val context: Context,
    private val subCategoriesProduct: List<Product>
) : RecyclerView.Adapter<SubCategoryProductRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.category_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = subCategoriesProduct.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val subCategoryProduct = subCategoriesProduct[position]
        with(holder) {
            subcategoryProductName?.let {
                it.text = subCategoryProduct.name
            }
            Glide.with(context)
                .load(subCategoryProduct.main_image)
                .into(subcategoryProductImage!!)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val subcategoryProductImage: ImageView? =
            itemView.findViewById(R.id.imageView_categoryImage)
        val subcategoryProductName: TextView? = itemView.findViewById(R.id.tv_categoryName)
    }
}