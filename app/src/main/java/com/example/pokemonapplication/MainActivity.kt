package com.example.pokemonapplication

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.PokemonSpeciesQuery
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val pokemonViewModel: PokemonViewModel by lazy {
        ViewModelProvider(this)[PokemonViewModel::class.java]
    }
    private lateinit var searchEditText: EditText
    private lateinit var searchButton: Button
    private lateinit var searchResultRecyclerView: RecyclerView
    private lateinit var loadingIndicator: ProgressBar
    private lateinit var searchTerm: String
    private lateinit var pokemonSpeciesAdapter: PokemonSpeciesAdapter
    private val debounceTimeMillis = 500L // Adjust the debounce time as needed
    private var debounceJob: Job? = null // Avoid multiple api call while scrolling

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        searchEditText = findViewById(R.id.searchEditText)
        searchButton = findViewById(R.id.searchButton)
        searchResultRecyclerView = findViewById(R.id.searchResultRecyclerView)
        loadingIndicator = findViewById(R.id.loadingIndicator)

        // RecyclerView setup
        searchResultRecyclerView.layoutManager = LinearLayoutManager(this)
        pokemonSpeciesAdapter = PokemonSpeciesAdapter()
        searchResultRecyclerView.adapter = pokemonSpeciesAdapter

        // Observe changes in isLoading
        pokemonViewModel.isLoading.observe(this) { isLoading ->
            loadingIndicator.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        // Observe changes in speciesNameList and update UI accordingly
        pokemonViewModel.speciesNameList.observe(this) {
            handleSpeciesNameListChanges(it)
        }

        // Add a scroll listener to RecyclerView to detect when to load more data
        searchResultRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                handleRecyclerViewScroll()
            }
        })

//        searchEditText.addTextChangedListener {
//            val content = it.toString()
//            searchButton.isEnabled = content.isNotEmpty()
//        }

        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                searchButton.isEnabled = !p0.isNullOrEmpty()
            }
        })


        searchButton.setOnClickListener {
            hideKeyboard()
            searchTerm = searchEditText.text.toString()
            Log.d("ApolloGraphqlActivity", searchTerm)
            pokemonSpeciesAdapter.clearData()
            searchResultRecyclerView.visibility = View.GONE
            pokemonViewModel.getSpeciesNameList(searchTerm)
        }
    }

    private fun handleRecyclerViewScroll() {
        if (debounceJob?.isActive == true) {
            return // Ignore if already loading or debounce is active
        }

        debounceJob = CoroutineScope(Dispatchers.Main).launch {
            delay(debounceTimeMillis)
            val layoutManager = searchResultRecyclerView.layoutManager as LinearLayoutManager
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

            val isEndOfList = visibleItemCount + firstVisibleItemPosition >= totalItemCount
            if (isEndOfList) {
                pokemonViewModel.loadMoreData()
            }
        }
    }

    private fun handleSpeciesNameListChanges(speciesList: List<PokemonSpeciesQuery.Pokemon_v2_pokemonspeciesname>) {
        // Update UI based on speciesList changes
        if (speciesList.isNotEmpty()) {
            searchResultRecyclerView.visibility = View.VISIBLE
            if (pokemonSpeciesAdapter.itemCount == 0) {
                // Initial data set
                pokemonSpeciesAdapter.setData(speciesList)
            } else {
                // Load more data when the end of the list is reached
                pokemonSpeciesAdapter.loadMoreData(speciesList)
            }
        } else {
            // Handle other cases, e.g., show a message, handle the end of the list
            if (pokemonSpeciesAdapter.itemCount == 0) {
                // No data available
                Snackbar.make(
                    searchResultRecyclerView,
                    "No matching data found.",
                    Snackbar.LENGTH_SHORT
                ).show()
            } else {
                // End of the list, no more data to load
                Snackbar.make(
                    searchResultRecyclerView,
                    "End of the list.",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }
    }


    private fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(searchEditText.windowToken, 0)
    }
}
