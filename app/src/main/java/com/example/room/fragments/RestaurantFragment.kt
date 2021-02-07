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
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.room.model.Restaurant
import kotlinx.android.synthetic.main.fragment_restaurant.view.*
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.observe
import com.example.room.*
import com.example.room.constants.Constants
import com.example.room.model.CrossTable
import com.example.room.viewmodel.DaoViewModel
import com.example.room.viewmodel.MainViewModel
import java.util.*

class RestaurantFragment : Fragment() {

    private lateinit var viewModel: MainViewModel
    private lateinit var daoViewModel: DaoViewModel
    private var REQUEST_PHONE_CALL = 1

    // a vendéglő telefonszáma és linkje

    var phoneNumber = ""
    var url = ""

    // az adatokat felhasználom a Google térképnél

    companion object {
        var lat = 1.1
        var lng = 1.1
        var name = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_restaurant, container, false)

        daoViewModel = ViewModelProvider(this).get(DaoViewModel::class.java)

        // lekérem a "restaurant" kulcson keresztül azt a vendéglőt, amelyikre rákantittottam

        val restaurant = arguments?.getParcelable<Restaurant>("restaurant")!!

        // a kiválasztott vendéglőhöz tartozó kép betöltése

        Glide.with(view.this_restaurant_image_id)
            .load(restaurant.image_url)
            .into(view.this_restaurant_image_id)

        // a kiválasztott vendéglőhöz tartozó adatok

        view.this_restaurant_name_id.text = restaurant.name
        view.this_restaurant_address_id.text = "Address: " + restaurant.address
        view.this_restaurant_city_id.text = "City: " + restaurant.city
        view.this_restaurant_state_id.text = "State: " + restaurant.state
        view.this_restaurant_area_id.text = "Area: " + restaurant.area
        view.this_restaurant_postal_code_id.text = "Postal code: " + restaurant.postal_code
        view.this_restaurant_country_id.text = "Country: " + restaurant.country

        // ezeket az adatokat használom a híváshoz, weblaphoz és térképhez

        phoneNumber = restaurant.phone
        lat = restaurant.lat
        lng = restaurant.lng
        url = restaurant.reserve_url
        name = restaurant.area

        // hívás

        view.this_restaurant_call_id.setOnClickListener {
            if(ActivityCompat.checkSelfPermission(this.requireContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this.requireActivity(), arrayOf(Manifest.permission.CALL_PHONE), REQUEST_PHONE_CALL)
            } else {
                startCall()
            }
        }

        // térkép

        view.this_restaurant_locate_id.setOnClickListener {

            val transaction =
                (context as FragmentActivity).supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment, MapsFragment())
            transaction.commit()

        }

        // weboldal

        view.this_restaurant_website_id.setOnClickListener {
            val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))

            startActivity(webIntent)
        }

        view.this_restaurant_favourite_id.setOnClickListener {

            daoViewModel.addRestaurantDB(restaurant)

            var isFavourite: Boolean = false
            daoViewModel.readAllCross.observe(viewLifecycleOwner) { cross ->

                // megnézem, hogy kedvenc vendéglő e már vagy sem

                for (v in cross) {
                    if (v.id == restaurant.id && v.userID == Constants.idPerson) {
                        isFavourite = true
                        break
                    }
                }

                // ha nincs hozzaadva a kedvencekhez, akkor hozzáadom

                if (!isFavourite) {
                    val crossID: Int = Random().nextInt(1000)
                    Constants.restaurantList.add(restaurant)
                    daoViewModel.addUserRestaurantDB(
                        CrossTable(
                            crossID,
                            restaurant.id,
                            Constants.idPerson
                        )
                    )
                    Toast.makeText(context, "Favourite!", Toast.LENGTH_SHORT).show()
                }
            }

        }

        // Inflate the layout for this fragment
        return view
    }

    // hívás indítása

    private fun startCall() {
        val callIntent = Intent(Intent.ACTION_CALL)

        callIntent.data = Uri.parse("tel:" + phoneNumber)

        startActivity(callIntent)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if(requestCode == REQUEST_PHONE_CALL) startCall()
    }

}

