package com.jonathan.santos.marvelchallenge.data

import com.google.gson.annotations.SerializedName

data class ComicsContainer(
    @SerializedName("items")
    val comicsList: List<Comic>
)
