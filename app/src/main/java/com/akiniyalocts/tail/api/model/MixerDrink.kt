package com.akiniyalocts.tail.api.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MixerDrink(
    @Json(name = "strDrink")
    val name: String,
    @Json(name = "strDrinkThumb")
    val thumbnail: String,
    @Json(name = "idDrink")
    val id: String
)
