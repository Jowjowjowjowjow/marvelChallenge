package com.jonathan.santos.marvelchallenge.data

import com.google.gson.annotations.SerializedName

data class SeriesContainer(
    @SerializedName("items")
    val seriesList: List<Serie>
)
