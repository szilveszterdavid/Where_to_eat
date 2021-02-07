package com.example.room.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.example.room.constants.Constants
import com.example.room.viewmodel.DaoViewModel
import com.example.room.R
import com.example.room.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.home_fragment.*
import kotlinx.android.synthetic.main.home_fragment.view.*
import kotlinx.coroutines.runBlocking

class HomeFragment : Fragment() {

    private lateinit var mUserViewModel: UserViewModel
    private lateinit var daoViewModel: DaoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.home_fragment, container, false)

        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        daoViewModel = ViewModelProvider(this).get(DaoViewModel::class.java)

        // blokkoló függvény

        runBlocking {

            // köztes tábla

            daoViewModel.readAllCross.observe(viewLifecycleOwner) { cross->
                runBlocking {

                    // összes User

                    daoViewModel.readAllData.observe(viewLifecycleOwner) { usr->
                        runBlocking {

                            // összes Restaurant

                            daoViewModel.readAllRestaurant.observe(viewLifecycleOwner) { rest->

                                // bejelentkezés

                                val login = view.findViewById<Button>(R.id.home_login_id)
                                login.setOnClickListener {

                                    val email = sign_in_email_id.text.toString()
                                    val password = sign_in_password_id.text.toString()

                                    // lekérem az adatbázisból az adott emailnak megfelelő jelszót

                                    val databasePassword = mUserViewModel.correctPassword(email)

                                    // ha ez a jelszó megegyezik a beírt jelszóval, akkor be tudok lépni

                                    if(databasePassword == password){
                                        Constants.idPerson = mUserViewModel.thisPersonId(email)

                                        // megkeresem a köztes táblába a Usert és az ő kedvenc vendéglőit

                                        for(v in cross){
                                            if(v.userID == Constants.idPerson){
                                                for(w in rest){
                                                    if(v.id == w.id){

                                                        // kimentem egy listába

                                                        Constants.restaurantList.add(w)
                                                    }
                                                }
                                            }
                                        }

                                        // bejelentkezés után átmegyünk a vendéglők listájához

                                        val transaction =
                                            (context as FragmentActivity).supportFragmentManager.beginTransaction()
                                        transaction.replace(R.id.fragment, MainFragment())
                                        transaction.commit()

                                    } else {

                                        // nincs ilyen felhasználó

                                        Toast.makeText(requireContext(), "Error login!", Toast.LENGTH_LONG).show()
                                    }

                                }
                            }
                        }
                    }
                }
            }
        }

        // átvisz a regisztrációra

        view.home_register_id.setOnClickListener {
            val transaction = (context as FragmentActivity).supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment, RegisterFragment())
            transaction.commit()
        }

        return view
    }


}