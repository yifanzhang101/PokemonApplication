package com.example.pokemonapplication

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.network.okHttpClient
import okhttp3.OkHttpClient

object ApolloService {
    private const val GRAPHQL_ENDPOINT = "https://beta.pokeapi.co/graphql/v1beta"
    private val okHttpClient = OkHttpClient()

    val apolloClient by lazy {
        ApolloClient.Builder()
            .serverUrl(GRAPHQL_ENDPOINT)
            .okHttpClient(okHttpClient)
            .build()
    }
}
