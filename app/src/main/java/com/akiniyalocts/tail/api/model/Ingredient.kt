package com.akiniyalocts.tail.api.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Ingredient(
    @Json(name = "idIngredient")
    val id: String,
    @Json(name = "strABV")
    val abv: String,
    @Json(name = "strAlcohol")
    val alcohol: String,
    @Json(name = "strDescription")
    val description: String,
    @Json(name = "strIngredient")
    val ingredient: String,
    @Json(name = "strType")
    val type: String
)