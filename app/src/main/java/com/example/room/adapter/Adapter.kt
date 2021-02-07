package com.example.room.adapter

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
import com.example.room.R
import com.example.room.fragments.MainFragment
import com.example.room.fragments.RestaurantFragment
import com.example.room.model.Restaurant
import com.example.room.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.restaurant.view.*

class Adapter(
    private var List: List<Restaurant>,
    private val listener: MainFragment,
    private val context: Context
                ) :
    RecyclerView.Adapter<Adapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.restaurant, parent, false)

        return ItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

        val currentItem = List[position]

        // képek illetve a vendéglők neveinek betöltése a recycle viewba

        Glide.with(holder.restaurantImage.context)
            .load(currentItem.image_url)
            .into(holder.restaurantImage)


        holder.restaurantName.text = currentItem.name

        // ha rámegyünk egy vendéglőre, akkor az adatokat át kell vinni a Restaurant fragmentre

        holder.itemView.setOnClickListener {

            val bundle = Bundle()
            bundle.putParcelable("restaurant", currentItem) // "restaurant" lesz a kulcs, amivel majd megkapjuk az adatokat

            var restaurant = RestaurantFragment()
            restaurant.arguments = bundle

            // átmegyünk a vendéglő részletes oldalára

            val transaction = (context as FragmentActivity).supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment, restaurant)
            transaction.commit()
        }

    }

    override fun getItemCount() = List.size

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val restaurantImage: ImageView = itemView.item_restaurant_picture_id
        val restaurantName: TextView = itemView.item_restaurant_name_id
    }

    fun setData(restaurants: MutableList<Restaurant>){
        this.List = restaurants
        notifyDataSetChanged()
    }

}