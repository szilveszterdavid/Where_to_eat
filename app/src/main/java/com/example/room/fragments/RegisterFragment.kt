package com.example.room.fragments

import android.os.Bundle
import android.text.TextUtils
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.room.R
import com.example.room.model.User
import com.example.room.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.register_fragment.*
import kotlinx.android.synthetic.main.register_fragment.view.*

class RegisterFragment : Fragment() {

    private lateinit var mUserViewModel: UserViewModel

    private var mIsShowPass = false

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.register_fragment, container, false)

        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        // regisztráció

        view.sign_up_button.setOnClickListener{
            insertDataToDatabase()
        }

        // jelszó elrejtése, megjelenítése

        view.show_pass_id.setOnClickListener {
            mIsShowPass = !mIsShowPass
            showPassword(mIsShowPass)
        }

        return view
    }

    private fun showPassword(isShow: Boolean) {
        if(isShow){
            addPassword_Et.transformationMethod = HideReturnsTransformationMethod.getInstance()
            show_pass_id.setImageResource(R.drawable.ic_baseline_visibility_off_24)
        } else {
            addPassword_Et.transformationMethod = PasswordTransformationMethod.getInstance()
            show_pass_id.setImageResource(R.drawable.ic_baseline_visibility_24)
        }
        addPassword_Et.setSelection(addPassword_Et.text.toString().length)
    }

    private fun insertDataToDatabase(){
        val firstName = addFirstname_et.text.toString()
        val lastName = addLastname_et.text.toString()
        val address = addAddress_et.text.toString()
        val email = addEmail_Et.text.toString()
        val password = addPassword_Et.text.toString()
        val phone = addPhoneNumber_et.text.toString()
        if(inputCheck(firstName, lastName, address, email, password, phone)){
            // felhasználó létrehozása
            val user = User(
                0,
                firstName,
                lastName,
                address,
                email,
                password,
                phone
            )

            // felhasználó hozzáadása az adatbázishoz

            mUserViewModel.addUser(user)
            Toast.makeText(requireContext(), "Successfully registered!", Toast.LENGTH_LONG).show()

            // regisztráció után visszaugrunk az előző oldalra
            findNavController().navigate(R.id.action_registerFragment_to_homeFragment)
        } else {

            // nincs kitöltve minden mező

            Toast.makeText(requireContext(), "Please fill out all fields!", Toast.LENGTH_LONG).show()
        }
    }

    // regisztrációs adatok tesztelése

    private fun inputCheck(firstName: String, lastName: String, address: String, email: String, password: String, phone: String): Boolean{
        return !(TextUtils.isEmpty(firstName) && TextUtils.isEmpty(lastName) && address.isEmpty() && email.isEmpty() && password.isEmpty() && phone.isEmpty())
    }

}