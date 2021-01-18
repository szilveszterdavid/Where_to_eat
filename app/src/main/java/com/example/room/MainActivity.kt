package com.example.room

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.room.MainViewModel
import com.example.room.repository.Repository
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.main_fragment.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()

        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        /*
        viewModel.getCities()

        viewModel.myResponseCities.observe(this, Observer { response ->
            Log.d("Response", response.cities[0])
        })
        */



        /*

        viewModel.getCountries()

        viewModel.myResponseCountries.observe(this, Observer { response ->
            Log.d("Response", response.count.toString())
            Log.d("Response", response.countries[0])
        })
        */



        viewModel.getRestaurants()

        viewModel.myResponseRestaurants.observe(this, Observer { response ->
            Log.d("Response", response.restaurants[0].image_url)
        })
        


        //setupActionBarWithNavController(findNavController(R.id.fragment))

        //val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        //val navController = findNavController(R.id.fragment)
        //bottomNavigationView.setupWithNavController(navController)

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.fragment)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

}

