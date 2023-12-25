package com.example.pokemonapplication

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.PokemonSpeciesQuery

class PokemonSpeciesAdapter: RecyclerView.Adapter<PokemonSpeciesAdapter.ViewHolder>() {

    private var speciesNameList: List<PokemonSpeciesQuery.Pokemon_v2_pokemonspeciesname> = emptyList()

    fun setData(newData: List<PokemonSpeciesQuery.Pokemon_v2_pokemonspeciesname>) {
        speciesNameList = newData
        notifyDataSetChanged()
    }

    fun loadMoreData(newDataList: List<PokemonSpeciesQuery.Pokemon_v2_pokemonspeciesname>) {
        val startPosition = speciesNameList.size
        speciesNameList = speciesNameList + newDataList
        notifyItemRangeInserted(startPosition, newDataList.size)
    }

    fun clearData() {
        speciesNameList = emptyList()
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val speciesItemLayout: com.google.android.material.card.MaterialCardView
        private val pokemonId: TextView
        private val pokemonSpeciesName: TextView
        private val pokemonGenus: TextView
        private val pokemonSpeciesCaptureRate: TextView
        private val pokemonsName: TextView

        fun bindItems(speciesName: PokemonSpeciesQuery.Pokemon_v2_pokemonspeciesname?) {
            speciesName?.let {

                //if background is black, then set text to white
                speciesItemLayout.setCardBackgroundColor(
                    getColorCode(
                        speciesName.pokemon_v2_pokemonspecy?.pokemon_v2_pokemoncolor?.name
                            ?: "null"
                    )
                )

                val textColor = if (speciesName.pokemon_v2_pokemonspecy?.pokemon_v2_pokemoncolor?.name == "black") {
                    getColorCode("white")
                } else {
                    getColorCode("black")
                }

                pokemonId.setTextColor(textColor)
                pokemonSpeciesName.setTextColor(textColor)
                pokemonGenus.setTextColor(textColor)
                pokemonSpeciesCaptureRate.setTextColor(textColor)
                pokemonsName.setTextColor(textColor)

                //TODO: set l18n in string values
                pokemonId.text = "ID: " + speciesName.id.toString()
                pokemonSpeciesName.text = "Species Name: " + speciesName.name
                pokemonGenus.text =
                    ("Species Genus: " + speciesName.genus.takeIf { it.isNotEmpty() }) ?: "null"
                pokemonSpeciesCaptureRate.text =
                    "Capture Rate: " + speciesName.pokemon_v2_pokemonspecy?.capture_rate.toString()
                pokemonsName.text =
                    "Pokemons Name: " + speciesName.pokemon_v2_pokemonspecy?.pokemon_v2_pokemons?.iterator()
                        ?.next()?.name
            }
        }

        init {
            speciesItemLayout = view.findViewById(R.id.speciesItemLayout)
            pokemonId = view.findViewById(R.id.pokemonId)
            pokemonSpeciesName = view.findViewById(R.id.pokemonSpeciesName)
            pokemonGenus = view.findViewById(R.id.pokemonGenus)
            pokemonSpeciesCaptureRate = view.findViewById(R.id.pokemonSpeciesCaptureRate)
            pokemonsName = view.findViewById(R.id.pokemonsName)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.pokemon_species_item, parent, false)
        val holder = ViewHolder(view)
        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, PokemonDetailActivity::class.java)
            val selectedPokemonSpecy = speciesNameList[holder.layoutPosition].toParcelable()
            val bundle = Bundle()
            bundle.putParcelable("pokemonSpecies", selectedPokemonSpecy)
            intent.putExtras(bundle)
            context.startActivity(intent)
        }
        return holder
    }


    override fun getItemCount(): Int {
        return speciesNameList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val species = speciesNameList[position]
        holder.bindItems(species)
    }
}
