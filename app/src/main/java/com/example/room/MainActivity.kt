package com.example.room

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.room.MainViewModel
import com.example.room.fragments.*
import com.example.room.repository.Repository
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.main_fragment.*
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val homeFragment = HomeFragment()
        val mainFragment = MainFragment()
        val mapsFragment = MapsFragment()
        val profilFragment = ProfilFragment()
        val registerFragment = RegisterFragment()
        val restaurantFragment = RestaurantFragment()

        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        viewModel.getCities()

        viewModel.myResponseCities.observe(this, Observer { response ->
            CitiesConstants.citiesList = response.cities as ArrayList<String>
        })

        /**Setting up the navigation logic.*/
        bottom_navigation.setOnNavigationItemSelectedListener {
            var selectedFragment: Fragment = homeFragment
            when (it.itemId) {
                R.id.ic_home -> selectedFragment = mainFragment
                R.id.ic_favorite -> selectedFragment = profilFragment
                R.id.ic_settings -> selectedFragment = profilFragment
            }

            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment, selectedFragment)
            transaction.commit()
            return@setOnNavigationItemSelectedListener true
        }
    }
}

