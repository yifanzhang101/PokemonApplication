package com.example.pokemonapplication

import android.os.Parcelable
import com.example.PokemonSpeciesQuery
import kotlinx.parcelize.Parcelize

@Parcelize
data class ParcelablePokemonSpeciesName(
    val id: Int,
    val name: String,
    val genus: String,
    val language_id: Int?,
    val pokemon_v2_pokemonspecy: ParcelablePokemonSpicy?
) : Parcelable

@Parcelize
data class ParcelablePokemonSpicy(
    val id: Int,
    val name: String,
    val capture_rate: Int?,
//    val pokemon_v2_pokemoncolor: PokemonQuery.Pokemon_v2_pokemoncolor?,
    val pokemon_v2_pokemons: List<ParcelablePokemon>
) : Parcelable

@Parcelize
data class ParcelablePokemon(
    val id: Int,
    val name: String,
    val pokemon_v2_pokemonabilities: List<ParcelablePokemonAbility>
) : Parcelable

@Parcelize
data class ParcelablePokemonAbility(
    val id: Int,
    val pokemon_v2_ability: ParcelableAbility?,
) : Parcelable

@Parcelize
data class ParcelableAbility(
    val name: String,
) : Parcelable

fun PokemonSpeciesQuery.Pokemon_v2_pokemonspeciesname.toParcelable(): ParcelablePokemonSpeciesName {
    return ParcelablePokemonSpeciesName(
        id = this.id,
        name = this.name,
        genus = this.genus,
        language_id = this.language_id,
        pokemon_v2_pokemonspecy = this.pokemon_v2_pokemonspecy?.toParcelablePokemonSpecy()
    )
}

fun PokemonSpeciesQuery.Pokemon_v2_pokemonspecy.toParcelablePokemonSpecy(): ParcelablePokemonSpicy {
    return ParcelablePokemonSpicy(
        id = this.id,
        name = this.name,
        capture_rate = this.capture_rate,
        pokemon_v2_pokemons = this.pokemon_v2_pokemons.map { it.toParcelablePokemons() }
    )
}

fun PokemonSpeciesQuery.Pokemon_v2_pokemon.toParcelablePokemons(): ParcelablePokemon {
    return ParcelablePokemon(
        id = this.id,
        name = this.name,
        pokemon_v2_pokemonabilities = this.pokemon_v2_pokemonabilities.map { it.toParcelableAbility() }
    )
}

fun PokemonSpeciesQuery.Pokemon_v2_pokemonability.toParcelableAbility(): ParcelablePokemonAbility {
    return ParcelablePokemonAbility(
        id = this.id,
        pokemon_v2_ability = this.pokemon_v2_ability?.toParcelableAbility()
    )
}

fun PokemonSpeciesQuery.Pokemon_v2_ability.toParcelableAbility(): ParcelableAbility {
    return ParcelableAbility(
        name = this.name
    )
}

