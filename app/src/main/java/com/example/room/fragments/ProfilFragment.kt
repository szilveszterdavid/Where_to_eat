package com.example.room.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.room.constants.Constants
import com.example.room.constants.Constants.Companion.idPerson
import com.example.room.viewmodel.DaoViewModel
import com.example.room.R
import com.example.room.adapter.FavouriteAdapter
import com.example.room.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_profil.view.*

class ProfilFragment : Fragment() {

    private lateinit var mUserViewModel: UserViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var daoViewModel: DaoViewModel

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

        view.profil_name_id.text = mUserViewModel.thisPersonFirstName(idPerson) + " " + mUserViewModel.thisPersonLastName(
            idPerson)

        view.profil_address_id.text = mUserViewModel.thisPersonAddress(idPerson)
        view.profil_phone_id.text = mUserViewModel.thisPersonPhone(idPerson)
        view.profil_email_id.text = mUserViewModel.thisPersonMail(idPerson)

        daoViewModel = ViewModelProvider(this).get(DaoViewModel::class.java)

        // a felhasználó kedvenc vendéglőinek megjelenítése

        recyclerView = view.fav_rest_rec
        view.fav_rest_rec.adapter = FavouriteAdapter(
            Constants.restaurantList,
            daoViewModel
        )
        view.fav_rest_rec.layoutManager = LinearLayoutManager(this.context)
        view.fav_rest_rec.setHasFixedSize(true)

        // Inflate the layout for this fragment
        return view

    }

}