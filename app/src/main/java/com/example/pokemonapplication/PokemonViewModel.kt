package com.example.pokemonapplication

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.PokemonSpeciesQuery
import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope

class PokemonViewModel: ViewModel() {
    companion object{
        const val INIT_PAGE_SIZE = 30 // Adjust the page size as needed
        const val PAGE_SIZE = 20 // Adjust the page size as needed
    }

    val speciesNameList: LiveData<List<PokemonSpeciesQuery.Pokemon_v2_pokemonspeciesname>> = Repository.speciesNameList
    val isLoading: LiveData<Boolean> = Repository.isLoading

    private var searchTerm: String = ""
    private var currentOffset = 0

    fun getSpeciesNameList(searchTerm: String) {
        this.searchTerm = searchTerm
        currentOffset = 0 // Reset offset when initiating a new search
        viewModelScope.launch {
            Repository.getSpeciesNameList(searchTerm, INIT_PAGE_SIZE, currentOffset)
        }
    }

    // Increment offset when loading more data
    fun loadMoreData() {
        currentOffset += PAGE_SIZE
        viewModelScope.launch {
            Repository.getSpeciesNameList(searchTerm, PAGE_SIZE, currentOffset)
        }
    }
}
