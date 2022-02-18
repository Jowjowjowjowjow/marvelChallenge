package com.jonathan.santos.marvelchallenge.data

import com.google.gson.annotations.SerializedName

data class Character(
    @SerializedName("id")
    val id: Int,
    @SerializedName("description")
    val description: String,
    @SerializedName("thumbnail")
    val thumbnail: CharacterThumbnail,
    @SerializedName("comics")
    val comicsContainer: ComicsContainer,
    @SerializedName("series")
    val seriesList: SeriesContainer

)
