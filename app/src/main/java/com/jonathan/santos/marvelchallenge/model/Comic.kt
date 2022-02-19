package com.jonathan.santos.marvelchallenge.model

import com.google.gson.annotations.SerializedName

data class Comic(
    @SerializedName("name")
    val name: String
)
