package com.example.finalproject

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.databinding.ChoiceRowBinding

class choiceAdapter(val items:ArrayList<String>)
    : RecyclerView.Adapter<choiceAdapter.ViewHolder>() {
    interface ItemClickListener{
        fun OnItemClick(position: Int){
        }
    }
    var itemClickListener:ItemClickListener? = null
    inner class ViewHolder(val binding: ChoiceRowBinding) : RecyclerView.ViewHolder(binding.root){
        init {
            binding.ingredient.setOnClickListener{
                itemClickListener?.OnItemClick(absoluteAdapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ChoiceRowBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.ingredient.text = items[position]
    }
}