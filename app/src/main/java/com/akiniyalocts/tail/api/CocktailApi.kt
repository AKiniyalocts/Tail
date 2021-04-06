package com.akiniyalocts.tail.api

import com.akiniyalocts.tail.BuildConfig
import com.akiniyalocts.tail.api.model.Drink
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CocktailApi {
    @GET("api/json/v2/{apiKey}/popular.php")
    suspend fun getPopularCocktails(@Path("apiKey") apiKey: String = BuildConfig.cocktailDbApiKey): Response<List<Drink>>

    @GET("api/json/v2/{apiKey}/search.php")
    suspend fun getSearchResults(@Path("apiKey") apiKey: String = BuildConfig.cocktailDbApiKey, @Query("s") query: String): Response<List<Drink>>
}