package com.example.pokemonapplication

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.apollographql.apollo3.api.Optional
import com.example.PokemonSpeciesQuery

object Repository {

    private var _speciesNameList = MutableLiveData<List<PokemonSpeciesQuery.Pokemon_v2_pokemonspeciesname>>()
    val speciesNameList: LiveData<List<PokemonSpeciesQuery.Pokemon_v2_pokemonspeciesname>> get() = _speciesNameList

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    suspend fun getSpeciesNameList(searchTerm: String, limit: Int?, offset: Int?) {
        _isLoading.value = true
        try {
            val response = ApolloService.apolloClient.query(
                PokemonSpeciesQuery(searchTerm = "%$searchTerm%", limit = Optional.present(limit), offset = Optional.present(offset))
            ).execute()
            val data = response.data
            Log.e("apolloRepository", "response.data: ${ response.data.toString()}")
            data?.let {
                _speciesNameList.postValue(it.pokemon_v2_pokemonspeciesname)
            }
        } catch (e: Exception) {
            Log.e("apolloRepository", "Error fetching data: ${e.message}")
        } finally {
            _isLoading.value = false
        }
    }
}
