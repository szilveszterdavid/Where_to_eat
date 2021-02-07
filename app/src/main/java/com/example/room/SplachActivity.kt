package com.example.room

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.room.constants.Constants
import com.example.room.repository.Repository
import com.example.room.viewmodel.MainViewModel
import com.example.room.viewmodel.MainViewModelFactory
import java.util.ArrayList

class SplachActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splach)

        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        // lekérem és kimentem a városokat egy listába még a bejelentkezés előtt, másképp túlterhelődik a rendszer

        viewModel.getCities()

        viewModel.myResponseCities.observe(this, Observer { response ->
            Constants.citiesList = response.cities as ArrayList<String>
        })

        Handler().postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 4000)

    }
}