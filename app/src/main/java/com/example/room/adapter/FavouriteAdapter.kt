package com.example.room.adapter

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.room.constants.Constants
import com.example.room.viewmodel.DaoViewModel
import com.example.room.R
import com.example.room.model.Restaurant
import kotlinx.android.synthetic.main.restaurant.view.*

class FavouriteAdapter (
    private var List: MutableList<Restaurant>,
    private var daoViewModel: DaoViewModel
) :
    RecyclerView.Adapter<FavouriteAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.restaurant, parent, false
        )

        return ItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

        val currentItem = List[position]

        Glide.with(holder.restaurantImage.context)
            .load(currentItem.image_url)
            .into(holder.restaurantImage)


        holder.restaurantName.text = currentItem.name

        holder.itemView.setOnLongClickListener {
            val builder = AlertDialog.Builder(holder.itemView.context)
            builder.setMessage("Delete?")
                .setCancelable(false)
                .setPositiveButton("Yes") { _, _ ->

                    // kitörlöm a kedvencek közül a vendéglőt

                    daoViewModel.deleteCrossDB(List[position].id, Constants.idPerson)

                    List.removeAt(position)
                    notifyItemRemoved(position)
                    notifyDataSetChanged()

                }
                .setNegativeButton("No") { dialog, _ ->
                    dialog.dismiss()
                }
            builder.create().show()

            return@setOnLongClickListener true
        }

    }

    override fun getItemCount() = List.size

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val restaurantImage: ImageView = itemView.item_restaurant_picture_id
        val restaurantName: TextView = itemView.item_restaurant_name_id

    }

}