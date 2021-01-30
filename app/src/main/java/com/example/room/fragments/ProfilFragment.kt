package com.example.room.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.room.R
import com.example.room.fragments.HomeFragment.Companion.idPerson
import com.example.room.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_profil.*
import kotlinx.android.synthetic.main.fragment_profil.view.*

class ProfilFragment : Fragment() {

    private lateinit var mUserViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_profil, container, false)

        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        // felhasználó adatainak betöltése

        view.profil_name_id.text = mUserViewModel.thisPersonFirstName(idPerson) + mUserViewModel.thisPersonLastName(
            idPerson)

        view.profil_address_id.text = mUserViewModel.thisPersonAddress(idPerson)
        view.profil_phone_id.text = mUserViewModel.thisPersonPhone(idPerson)
        view.profil_email_id.text = mUserViewModel.thisPersonMail(idPerson)

        // Inflate the layout for this fragment
        return view

    }

}