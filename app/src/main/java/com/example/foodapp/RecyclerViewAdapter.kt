package com.example.foodapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatDrawableManager.get
import androidx.appcompat.widget.ResourceManagerInternal.get
import androidx.recyclerview.widget.RecyclerView
import com.example.foodapp.api.FoodResponse
import com.example.foodapp.api.Product
import com.squareup.picasso.Picasso

class RecyclerViewAdapter(
    private var myList : List<Product>
) : RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>() {


    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        val imgViewFood: ImageView = itemView.findViewById(R.id.single_img)
        val textViewFood: TextView = itemView.findViewById(R.id.single_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.single_food,parent,false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int { return myList.size }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val food = myList[position]

        holder.textViewFood.text = food.title
        Picasso.get().load(food.image).resize(200,200).centerCrop().into(holder.imgViewFood)

    }

}