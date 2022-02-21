package com.jonathan.santos.marvelchallenge.presentation.charactersDetails

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.jonathan.santos.marvelchallenge.R
import com.jonathan.santos.marvelchallenge.model.Character

class CharactersDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_characters_details)
        val extras = intent.extras
        if (extras !== null) {
            val character = (extras.getSerializable("Character") as Character)
            /*TODO: A partir do id do personagem, buscar as informações de comics e series dos
               endpoints GET /v1/public/characters/{characterId}/comics e
               GET /v1/public/characters/{characterId}/series */
            Toast.makeText(this, character.name, Toast.LENGTH_SHORT).show()
        }

    }
}