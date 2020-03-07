package br.com.deciomontanhani.pokedex.view.form

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.deciomontanhani.pokedex.api.PokemonRepository
import br.com.deciomontanhani.pokedex.model.Pokemon
import br.com.deciomontanhani.pokedex.view.ViewState

class FormPokemonViewModel(
    val pokemonRepository: PokemonRepository
) : ViewModel() {
    var viewState: MutableLiveData<ViewState<String>> = MutableLiveData()
    val isLoading = MutableLiveData<Boolean>()
    val messageResponse = MutableLiveData<String>()

    fun updatePokemon(pokemon: Pokemon) {
        viewState.value = ViewState.Loading
        pokemonRepository.updatePokemon(
            pokemon = pokemon,
            onComplete = {
                viewState.value = ViewState.Success("Os dados foram atualizados com sucesso")
            },
            onError = {
                viewState.value = ViewState.Failed(it)
            }
        )
    }
}