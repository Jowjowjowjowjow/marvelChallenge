package com.jonathan.santos.marvelchallenge.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Serie(
    @SerializedName("name")
    val name: String
): Serializable
