package com.jonathan.santos.marvelchallenge.model

import com.google.gson.annotations.SerializedName

data class Character(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("thumbnail")
    val thumbnail: CharacterThumbnail,
    @SerializedName("comics")
    val comicsContainer: ComicsContainer,
    @SerializedName("series")
    val seriesList: SeriesContainer
)
