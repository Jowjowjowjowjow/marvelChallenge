package com.jonathan.santos.marvelchallenge.model

import com.google.gson.annotations.SerializedName

data class CharacterDataWrapper(
    @SerializedName("data")
    val data: CharacterDataContainer
)
