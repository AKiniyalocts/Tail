package com.akiniyalocts.tail.api

import com.akiniyalocts.tail.api.model.Drink
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DrinksResponse(
    @Json(name = "drinks")
    val drinks: List<Drink>
)