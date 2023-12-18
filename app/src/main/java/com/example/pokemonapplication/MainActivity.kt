package com.example.pokemonapplication

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var searchEditText: EditText
    private lateinit var searchButton: Button
    private lateinit var searchResultRecyclerView: RecyclerView
    private lateinit var loadingIndicator: ProgressBar
    private lateinit var searchTerm: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        searchEditText = findViewById(R.id.searchEditText)
        searchButton = findViewById(R.id.searchButton)
        searchResultRecyclerView = findViewById(R.id.searchResultRecyclerView)
        loadingIndicator = findViewById(R.id.loadingIndicator)

        searchEditText.addTextChangedListener {
            val content = it.toString()
            searchButton.isEnabled = content.isNotEmpty()
        }

        searchButton.setOnClickListener {
            hideKeyboard()
            searchTerm = searchEditText.text.toString()
            Snackbar.make(searchEditText, searchTerm, Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(searchEditText.windowToken, 0)
    }
}
