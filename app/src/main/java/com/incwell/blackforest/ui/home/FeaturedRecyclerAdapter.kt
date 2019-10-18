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
import com.incwell.blackforest.data.model.Product


class FeaturedRecyclerAdapter(
    private val context: Context,
    private val products: List<Product>,
    private val itemListener: FeaturedItemListener
) :
    RecyclerView.Adapter<FeaturedRecyclerAdapter.ViewHolder>() {

    private lateinit var view: View

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        view = inflater.inflate(R.layout.featured_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = products.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = products[position]
        with(holder) {
            productName?.let {
                it.text = product.name
            }
            productDescription?.let {
                it.text = product.description
            }
            Glide.with(context)
                .load(product.main_image)
                .into(productImage!!)

            itemView.setOnClickListener {
                itemListener.onFeaturedItemClick(product)
            }
        }

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productImage: ImageView? = itemView.findViewById(R.id.imageView_productImage)
        val productName: TextView? = itemView.findViewById(R.id.tv_productName)
        val productDescription: TextView? = itemView.findViewById(R.id.tv_productDescription)
    }

    interface FeaturedItemListener {
        fun onFeaturedItemClick(product: Product)
    }
}