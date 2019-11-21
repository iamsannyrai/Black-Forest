package com.incwell.blackforest.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.incwell.blackforest.R
import com.incwell.blackforest.data.model.Category

class HomeCategoryRecyclerAdapter(
    val context: Context,
    var isFromHome:Boolean,
    val categories: List<Category>,
    val itemListenerHome: HomeCategoryItemListener
) :
    RecyclerView.Adapter<HomeCategoryRecyclerAdapter.ViewHolder>() {

    private lateinit var view: View

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        view = if (isFromHome) {
            inflater.inflate(R.layout.category_list_item, parent, false)
        } else {
            inflater.inflate(R.layout.category_grid_item, parent, false)
        }

        return ViewHolder(view)
    }

    override fun getItemCount(): Int = categories.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = categories[position]
        with(holder) {
            categoryName?.let {
                it.text = category.name
            }
            Glide.with(context)
                .load(category.category_image)
                .into(categoryImage!!)

            itemView.setOnClickListener {
                itemListenerHome.onHomeCategoryItemClick(category)
            }
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val categoryImage: ImageView? = itemView.findViewById(R.id.imageView_categoryImage)
        val categoryName: TextView? = itemView.findViewById(R.id.tv_categoryName)
    }

    interface HomeCategoryItemListener {
        fun onHomeCategoryItemClick(category: Category)
    }
}