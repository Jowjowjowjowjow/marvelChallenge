package com.jonathan.santos.marvelchallenge.data

import com.google.gson.annotations.SerializedName

data class Comic(
    @SerializedName("name")
    val name: String
)
