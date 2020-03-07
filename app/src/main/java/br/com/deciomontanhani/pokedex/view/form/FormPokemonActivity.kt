package br.com.deciomontanhani.pokedex.view.form

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import android.widget.ViewSwitcher
import androidx.lifecycle.Observer
import br.com.deciomontanhani.pokedex.R
import br.com.deciomontanhani.pokedex.model.Pokemon
import br.com.deciomontanhani.pokedex.view.ViewState
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_form_pokemon.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class FormPokemonActivity : AppCompatActivity() {
    val formPokemonViewModel: FormPokemonViewModel by viewModel()
    val picasso: Picasso by inject()
    lateinit var pokemon : Pokemon
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_pokemon)
        setValues()

        formPokemonViewModel.viewState.observe(this, Observer {
            when(it) {
                is ViewState.Loading -> {
                    btSaveForm.isEnabled = false
                }
                is ViewState.Failed -> {
                    btSaveForm.isEnabled = true
                    val message = it.throwable.message
                    if (message != "") Toast.makeText(this, message, Toast.LENGTH_LONG).show()
                }
                is ViewState.Success -> {
                    setResult(Activity.RESULT_OK)
                    finish()
                }
            }
        })
        btSaveForm.setOnClickListener {
            pokemon.ataque = sbAttack.progress
            pokemon.defesa = sbDefense.progress
            pokemon.velocidade = sbVelocity.progress
            pokemon.ps = sbPS.progress
            formPokemonViewModel.updatePokemon(pokemon)
        }
    }

    private fun setValues() {
        pokemon = intent.getParcelableExtra<Pokemon>("POKEMON")
        tvPokemonNameForm.text = pokemon.nome

        picasso.load("https://pokedexdx.herokuapp.com${pokemon.urlImagem}").into(ivPokemonForm)
        sbAttack.progress = pokemon.ataque
        sbDefense.progress = pokemon.defesa
        sbPS.progress = pokemon.ps
        sbVelocity.progress = pokemon.velocidade
        tvAttackValue.text = pokemon.ataque.toString()
        tvDefenseValue.text = pokemon.defesa.toString()
        tvPSValue.text = pokemon.ps.toString()
        tvVelocityValue.text = pokemon.velocidade.toString()
        setListener(sbAttack, tvAttackValue)
        setListener(sbDefense, tvDefenseValue)
        setListener(sbVelocity, tvVelocityValue)
        setListener(sbPS, tvPSValue)
    }

    private fun setListener(seekBar: SeekBar, textView: TextView) {
        seekBar.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser:
            Boolean) {
                textView.text = progress.toString()
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }

    companion object {
        val REQUEST_ALTERAR_DADOS = 200
    }
}
