package com.jonathan.santos.marvelchallenge.model

import com.google.gson.annotations.SerializedName

data class CharacterThumbnail(
    @SerializedName("path")
    val path: String,

    @SerializedName("extension")
    val extension: String
)
