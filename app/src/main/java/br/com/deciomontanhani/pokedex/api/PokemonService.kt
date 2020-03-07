package br.com.deciomontanhani.pokedex.api

import br.com.deciomontanhani.pokedex.model.HealthResponse
import br.com.deciomontanhani.pokedex.model.Pokemon
import br.com.deciomontanhani.pokedex.model.PokemonResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Query

interface PokemonService {
    @GET("/api/pokemon/health")
    fun checkHealth(): Call<HealthResponse>

    @GET("/api/pokemon")
    fun getPokemons(
        @Query("size") size: Int,
        @Query("sort") sort: String
    ): Call<PokemonResponse>

    @PUT("/api/pokemon")
    fun updatePokemon(
        @Body pokemon: Pokemon
    ) : Call<Pokemon>
}