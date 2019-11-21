package com.incwell.blackforest.ui.product

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.incwell.blackforest.R

class IngredientRecyclerAdapter(
    private val ingredients: List<String>
) : RecyclerView.Adapter<IngredientRecyclerAdapter.ViewHolder>() {

    private lateinit var view: View

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        view = inflater.inflate(R.layout.ingredient_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = ingredients.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ingredient = ingredients[position]
        with(holder){
            ingredientName?.let {
                it.text="\u2022 $ingredient"
            }
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ingredientName: TextView? = itemView.findViewById(R.id.ingredient)
    }
}