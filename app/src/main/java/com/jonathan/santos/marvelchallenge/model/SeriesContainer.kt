package com.jonathan.santos.marvelchallenge.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class SeriesContainer(
    @SerializedName("items")
    val seriesList: List<Serie>
): Serializable
