package com.example.room.fragments

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.room.MainViewModel
import com.example.room.MainViewModelFactory
import com.example.room.R
import com.example.room.fragments.MainFragment.Companion.restaurantId
import com.example.room.repository.Repository
import com.example.room.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_restaurant.*
import kotlinx.android.synthetic.main.fragment_restaurant.view.*
import kotlinx.android.synthetic.main.main_fragment.*

class RestaurantFragment : Fragment() {

    private lateinit var viewModel: MainViewModel

    val REQUEST_PHONE_CALL = 1

    var phoneNumber = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_restaurant, container, false)

        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        viewModel.getRestaurants()

        viewModel.myResponseRestaurants.observe(viewLifecycleOwner, Observer { response ->

            Glide.with(view.this_restaurant_image_id)
                .load(response.restaurants[restaurantId].image_url)
                .into(view.this_restaurant_image_id)

            view.this_restaurant_name_id.text = response.restaurants[restaurantId].name
            view.this_restaurant_address_id.text = "Address: " + response.restaurants[restaurantId].address
            view.this_restaurant_city_id.text = "City: " + response.restaurants[restaurantId].city
            view.this_restaurant_state_id.text = "State: " + response.restaurants[restaurantId].state
            view.this_restaurant_area_id.text = "Area: " + response.restaurants[restaurantId].area
            view.this_restaurant_postal_code_id.text = "Postal code: " + response.restaurants[restaurantId].postal_code
            view.this_restaurant_country_id.text = "Country: " + response.restaurants[restaurantId].country

            phoneNumber = response.restaurants[restaurantId].phone

        })

        view.this_restaurant_call_id.setOnClickListener {
            if(ActivityCompat.checkSelfPermission(view.context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(
                    this.requireActivity(),
                    arrayOf(Manifest.permission.CALL_PHONE),
                    REQUEST_PHONE_CALL
                )
            } else {
                startCall()
            }
        }

        // Inflate the layout for this fragment
        return view
    }

    private fun startCall() {
        val callIntent = Intent(Intent.ACTION_CALL)

        callIntent.data = Uri.parse("tel:" + phoneNumber)

        startActivity(callIntent)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if(requestCode == REQUEST_PHONE_CALL) startCall()
    }

}