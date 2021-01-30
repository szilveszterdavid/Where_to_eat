package com.example.room.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.room.R
import com.example.room.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.home_fragment.*
import kotlinx.android.synthetic.main.home_fragment.view.*

class HomeFragment : Fragment() {

    private lateinit var mUserViewModel: UserViewModel

    companion object {
        var idPerson = -1
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.home_fragment, container, false)

        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        view.home_register_id.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_registerFragment)
        }

        view.home_login_id.setOnClickListener {
            login()
        }

        return view
    }

    // bejelentkezés

    private fun login() {
        val email = sign_in_email_id.text.toString()
        val password = sign_in_password_id.text.toString()

        val databasePassword = mUserViewModel.correctPassword(email)

        if(databasePassword == password){
            idPerson = mUserViewModel.thisPersonId(email)
            findNavController().navigate(R.id.action_homeFragment_to_mainFragment)
        } else {

            // nincs ilyen felhasználó

            Toast.makeText(requireContext(), "Error login!", Toast.LENGTH_LONG).show()
        }
    }


}