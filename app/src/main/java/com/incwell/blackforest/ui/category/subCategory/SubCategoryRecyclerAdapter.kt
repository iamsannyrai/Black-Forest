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
import com.incwell.blackforest.data.model.SubCategory

class SubCategoryRecyclerAdapter(
    val context: Context,
    private val subCategories: List<SubCategory>
) :
    RecyclerView.Adapter<SubCategoryRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.subcategory_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = subCategories.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val subCategory = subCategories[position]
        with(holder) {
            subCategoryName?.let {
                it.text = subCategory.sub_category
            }
            Glide.with(context)
                .load(subCategory.image)
                .into(subCategoryImage!!)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val subCategoryName: TextView? = itemView.findViewById(R.id.tv_subCategoryName)
        val subCategoryImage: ImageView? = itemView.findViewById(R.id.imgv_subCategoryImage)
    }
}