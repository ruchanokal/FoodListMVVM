package com.ruchanokal.foodlistmvvm

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ruchanokal.foodlistmvvm.databinding.RecyclerviewRowBinding

class FoodRecyclerAdapter(val foodList : ArrayList<FoodModel>) : RecyclerView.Adapter<FoodRecyclerAdapter.FoodHolder>() {


    class FoodHolder(val binding: RecyclerviewRowBinding) : RecyclerView.ViewHolder(binding.root){

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodRecyclerAdapter.FoodHolder {

        val binding = RecyclerviewRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return FoodHolder(binding)
    }

    override fun onBindViewHolder(holder: FoodRecyclerAdapter.FoodHolder, position: Int) {

        val imageView = holder.itemView.findViewById<ImageView>(R.id.yemekImageView)

        holder.binding.yemekNameText.text = foodList.get(position).yemekName
        holder.binding.yemekYoresiText.text = foodList.get(position).yemekYoresi
        println("url: " + foodList.get(position).yemekImageUrl)

        try {
            Glide.with(holder.itemView.context).load(foodList.get(position).yemekImageUrl).into(imageView)
        } catch (e : Exception) {
            e.printStackTrace()
            println("hataaa: " + e.localizedMessage)
        }

    }

    override fun getItemCount(): Int {
        return foodList.size
    }

    fun updateList(newFoodList: List<FoodModel>) {
        foodList.clear()
        foodList.addAll(newFoodList)
        notifyDataSetChanged()
    }
}