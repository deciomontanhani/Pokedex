package br.com.deciomontanhani.pokedex.view.detail

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import br.com.deciomontanhani.pokedex.R
import br.com.deciomontanhani.pokedex.view.ViewState
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*

class DetailActivity : AppCompatActivity() {

    val detailViewModel: DetailViewModel by viewModel()
    val picasso: Picasso by inject()

    private lateinit var tts: TextToSpeech

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        initTTTS()

        detailViewModel.getPokemon(intent.getStringExtra("POKEMON_NUMBER"))

        detailViewModel.viewState.observe(this, Observer { state ->
            when(state) {
                is ViewState.Loading -> {

                }

                is ViewState.Success -> {
                    state.data?.let {
                        ivLoading.visibility = View.GONE
                        picasso.load("https://pokedexdx.herokuapp.com${it.urlImagem}").into(ivPokemon)
                        tvPokemonName.text = "${it.numero} ${it.nome}"
                        tvPokemonDescription.text = it.descricao

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            tts.speak(it.descricao, TextToSpeech.QUEUE_FLUSH, null, null)
                        } else {
                            tts.speak(it.descricao, TextToSpeech.QUEUE_FLUSH, null)
                        }
                    }
                }

                is ViewState.Failed -> {
                    ivLoading.visibility = View.GONE
                    Toast.makeText(this, "Erro ao carregar Pokemon", Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun initTTTS() {
        tts = TextToSpeech(this, TextToSpeech.OnInitListener {
            if(it!= TextToSpeech.ERROR) {
                tts.language = Locale("pt", "PT")
            }
        })
    }
}
