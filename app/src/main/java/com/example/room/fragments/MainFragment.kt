package com.example.room.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.room.*
import com.example.room.adapter.Adapter
import com.example.room.constants.Constants
import com.example.room.model.Restaurant
import com.example.room.repository.Repository
import com.example.room.spinner.CustomSearchableSpinner
import com.example.room.viewmodel.MainViewModel
import com.example.room.viewmodel.MainViewModelFactory
import kotlinx.android.synthetic.main.main_fragment.view.*
import java.util.*
import kotlin.collections.ArrayList

class MainFragment : Fragment() {

    private lateinit var viewModel: MainViewModel
    private lateinit var recyclerView: RecyclerView

    private var adapter: Adapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        // Inflate the layout for this fragment

        val view =  inflater.inflate(R.layout.main_fragment, container, false)

        val search = view.findViewById<TextView>(R.id.search_bar)

        // legördülő sáv a városokhoz

        val spinner = view.findViewById<Spinner>(R.id.spinner) as CustomSearchableSpinner
        val adapterSpinnerCity =
            activity?.let {
                ArrayAdapter(it, android.R.layout.simple_spinner_item, Constants.citiesList)
            }

        spinner.adapter = adapterSpinnerCity

        // legördülő sáv az oldalszámokhoz
        val pageNum = (1..10).toList()
        val spinnerPage = view.findViewById<Spinner>(R.id.spinnerPage) //as CustomSearchableSpinner
        val adapterSpinnerPage =
            activity?.let {
                ArrayAdapter(it, android.R.layout.simple_spinner_item, pageNum)
            }
        spinnerPage.adapter = adapterSpinnerPage

        var emptyList: java.util.ArrayList<Restaurant> = arrayListOf()

        adapter = context?.let { this.context?.let { it1 ->
            Adapter(
                emptyList,
                this,
                it1
            )
        } }

        recyclerView = view.restauants_id
        view.restauants_id.adapter = adapter
        view.restauants_id.layoutManager = LinearLayoutManager(this.context)
        view.restauants_id.setHasFixedSize(true)

        val repository = Repository()
        val viewModelFactory =
            MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        // vendéglők listázása városok szerint, majd azon belül az oldalak száma szerint
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ){
                // ha nincs kiválasztva az oldal
                viewModel.getAllRestaurant(
                    spinner.selectedItem.toString(),
                    spinnerPage.selectedItem as Int
                )
                search.text = ""

                // város és oldal szerint
                spinnerPage.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onNothingSelected(parent: AdapterView<*>?) {

                    }
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ){
                        viewModel.getAllRestaurant(
                            spinner.selectedItem.toString(),
                            spinnerPage.selectedItem as Int
                        )
                        search.text = ""
                    }
                }
            }
        }

        var mainList: ArrayList<Restaurant> = arrayListOf()

        // vendéglők frissítése
        viewModel.myResponseAll.observe(viewLifecycleOwner, Observer { response ->
            //if (response.isSuccess) {
                if (response.body()?.restaurants!!.isNotEmpty()) {
                    mainList = response.body()?.restaurants as ArrayList<Restaurant>
                    adapter?.setData(mainList)
                } else {
                    Toast.makeText(context, "Not existing page!", Toast.LENGTH_SHORT).show()
                    adapter?.setData(mutableListOf())
                }
            //}
        })

        // szűrés ár szerint
        search.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString().isNotEmpty()) {
                    adapter?.setData(mainList.filter {
                        it.price.toString().toLowerCase(Locale.ROOT).contains(
                            s.toString().toLowerCase(
                                Locale.ROOT
                            )
                        )
                    } as MutableList<Restaurant>)

                } else {
                    adapter?.setData(mainList)
                }
            }
        })


        return view
    }

}