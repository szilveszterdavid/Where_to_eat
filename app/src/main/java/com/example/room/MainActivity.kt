package com.example.room

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.room.constants.Constants
import com.example.room.fragments.*
import com.example.room.repository.Repository
import com.example.room.viewmodel.MainViewModel
import com.example.room.viewmodel.MainViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val homeFragment = HomeFragment()
        val mainFragment = MainFragment()
        val profilFragment = ProfilFragment()

        // navigálás a menu alapján

        bottom_navigation.setOnNavigationItemSelectedListener {
            var selectedFragment: Fragment = homeFragment
            when (it.itemId) {
                R.id.ic_home -> selectedFragment = mainFragment
                R.id.ic_profil -> selectedFragment = profilFragment
            }

            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment, selectedFragment)
            transaction.commit()
            return@setOnNavigationItemSelectedListener true
        }
    }
}

