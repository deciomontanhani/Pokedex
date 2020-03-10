package br.com.deciomontanhani.pokedex.view.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.deciomontanhani.pokedex.api.PokemonRepository
import br.com.deciomontanhani.pokedex.model.Pokemon
import br.com.deciomontanhani.pokedex.view.ViewState

class DetailViewModel(
    val pokemonRepository: PokemonRepository
) : ViewModel() {

    val viewState: MutableLiveData<ViewState<Pokemon?>> = MutableLiveData()

    fun getPokemon(number: String) {
        viewState.value = ViewState.Loading
        pokemonRepository.getPokemon(
            number,
            onComplete = {
                viewState.value = ViewState.Success(it)
            },
            onError = {
                viewState.value = ViewState.Failed(it)
            }
        )
    }
}