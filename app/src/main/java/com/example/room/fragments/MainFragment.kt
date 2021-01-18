package com.example.room.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.room.*
import com.example.room.model.Restaurant
import com.example.room.repository.Repository
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.android.synthetic.main.main_fragment.view.*

class MainFragment : Fragment(), Adapter.OnItemClickListener {

    private lateinit var viewModel: MainViewModel
    private lateinit var recyclerView: RecyclerView

    companion object {
        var restaurantId = -1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.main_fragment, container, false)

        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)


        viewModel.getRestaurants()

        viewModel.myResponseRestaurants.observe(viewLifecycleOwner, Observer { response ->
            recyclerView.adapter = Adapter(response.restaurants, this)
            viewModel.restaurantList.addAll(response.restaurants)
        })

        recyclerView = view.restauants_id
        view.restauants_id.layoutManager = LinearLayoutManager(this.context)
        view.restauants_id.setHasFixedSize(true)


        return view
    }

    override fun onItemClick(position: Int) {

        restaurantId = position

        findNavController().navigate(R.id.action_mainFragment_to_restaurantFragment)

        //Toast.makeText(this.context, "Item $position clicked", Toast.LENGTH_SHORT).show()
        val clickedItem: Restaurant = viewModel.restaurantList[position]
    }
}