package com.jonathan.santos.marvelchallenge.model

import com.google.gson.annotations.SerializedName

data class SeriesContainer(
    @SerializedName("items")
    val seriesList: List<Serie>
)
