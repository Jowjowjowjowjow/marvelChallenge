package com.jonathan.santos.marvelchallenge.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ComicsContainer(
    @SerializedName("items")
    val comicsList: List<Comic>
): Serializable
