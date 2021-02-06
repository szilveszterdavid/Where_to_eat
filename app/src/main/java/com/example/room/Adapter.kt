package com.example.room

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.room.fragments.MainFragment
import com.example.room.fragments.RestaurantFragment
import com.example.room.model.Countries
import com.example.room.model.Restaurant
import kotlinx.android.synthetic.main.main_fragment.view.*
import kotlinx.android.synthetic.main.restaurant.view.*

class Adapter(
    private var List: List<Restaurant>,
    private val listener: MainFragment,
    private val context: Context
                ) :
    RecyclerView.Adapter<Adapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.restaurant,
            parent, false)

        return ItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

        val currentItem = List[position]

        Glide.with(holder.restaurantImage.context)
            .load(currentItem.image_url)
            .into(holder.restaurantImage)


        holder.restaurantName.text = currentItem.name

        holder.itemView.setOnClickListener {

            val bundle = Bundle()
            bundle.putParcelable("restaurant", currentItem)

            var restaurant = RestaurantFragment()
            restaurant.arguments = bundle

            val transaction = (context as FragmentActivity).supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment, restaurant)
            transaction.commit()
        }

        //holder.restaurantAddress.text = currentItem.address
    }

    override fun getItemCount() = List.size

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val restaurantImage: ImageView = itemView.item_restaurant_picture_id
        val restaurantName: TextView = itemView.item_restaurant_name_id
        //val restaurantAddress: TextView = itemView.recycler_address
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }


    fun setData(restaurants: MutableList<Restaurant>){
        this.List = restaurants
        notifyDataSetChanged()
    }

}