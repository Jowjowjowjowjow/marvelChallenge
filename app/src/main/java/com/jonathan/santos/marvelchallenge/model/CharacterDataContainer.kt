package com.jonathan.santos.marvelchallenge.model

import com.google.gson.annotations.SerializedName


data class CharacterDataContainer (
    @SerializedName("results")
    val results: List<Character>
)