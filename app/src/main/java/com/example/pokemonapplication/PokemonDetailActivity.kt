package com.example.pokemonapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class PokemonDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon_detail)
        val bundle : Bundle? = intent.extras
        val pokemonSpeciesName = bundle?.getParcelable<ParcelablePokemonSpeciesName>("pokemonSpecies")
        val pokemonNameTextView: TextView = findViewById(R.id.pokemonDetailName)
        val pokemonAbilityNameTextView: TextView = findViewById(R.id.pokemonDetailAbilityName)
        val pokemonSpicy = pokemonSpeciesName?.pokemon_v2_pokemonspecy
        //TODO: use recycler view to display names and a back button
        pokemonSpicy?.let {
            pokemonNameTextView.text = it.name

            var s = ""
            val pokemons = it.pokemon_v2_pokemons
            for (pokemon in pokemons) {
                s += "pokemon.name: ${pokemon.name}\n"
                val pokemonAbilities = pokemon.pokemon_v2_pokemonabilities
                for (i in pokemonAbilities.indices) {
                    val pokemonAbility = pokemonAbilities[i]
                    if (pokemonAbility.pokemon_v2_ability != null) {
                        s += "ability${i + 1} :" + pokemonAbility.pokemon_v2_ability.name + "\n"
                    }
                }
            }
            pokemonAbilityNameTextView.text = s
        }
    }
}