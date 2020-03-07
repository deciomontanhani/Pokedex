package br.com.deciomontanhani.pokedex.view.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.deciomontanhani.pokedex.api.PokemonRepository
import br.com.deciomontanhani.pokedex.model.Pokemon
import br.com.deciomontanhani.pokedex.view.ViewState

enum class ListState {
    LOADING, SUCCESS, FAILURE
}

class ListPokemonsViewModel (val pokemonRepository: PokemonRepository) : ViewModel() {
    val viewState: MutableLiveData<ViewState<List<Pokemon>>> = MutableLiveData()
    fun getPokemons() {
        viewState.value = ViewState.Loading

        pokemonRepository.getPokemons(
            150, "number,asc", {
                viewState.value = ViewState.Success(it)
            }, {
                viewState.value = ViewState.Failed(it)
            }
        )
    }
}