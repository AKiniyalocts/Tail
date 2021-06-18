package com.akiniyalocts.tail.api

import com.akiniyalocts.tail.api.model.Drink
import com.akiniyalocts.tail.api.model.Ingredient
import com.akiniyalocts.tail.api.model.MixerDrink
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DrinksResponse(
    @Json(name = "drinks")
    val drinks: List<Drink>
)

@JsonClass(generateAdapter = true)
data class IngredientsResponse(
    @Json(name = "drinks")
    val ingredients: List<Ingredient>
)

@JsonClass(generateAdapter = true)
data class MixerDrinkResponse(
    @Json(name = "drinks")
    val drinks: List<MixerDrink>
)