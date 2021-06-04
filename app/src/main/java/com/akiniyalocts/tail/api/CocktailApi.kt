package com.akiniyalocts.tail.api

import com.akiniyalocts.tail.BuildConfig
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CocktailApi {
    @GET("api/json/v2/{apiKey}/popular.php")
    suspend fun getPopularCocktails(@Path("apiKey") apiKey: String = BuildConfig.cocktailDbApiKey): Response<DrinksResponse>

    @GET("api/json/v2/{apiKey}/search.php")
    suspend fun getSearchResults(@Path("apiKey") apiKey: String = BuildConfig.cocktailDbApiKey, @Query("s") query: String): Response<DrinksResponse>

    @GET("api/json/v2/{apiKey}/list.php?c=list")
    suspend fun getCategories(@Path("apiKey") apiKey: String = BuildConfig.cocktailDbApiKey): Response<CategoriesResponse>

    @GET("api/json/v2/{apiKey}/lookup.php")
    suspend fun getCocktail(@Path("apiKey") apiKey: String = BuildConfig.cocktailDbApiKey, @Query("i") id: String): Response<DrinksResponse>

    @GET("api/json/v2/{apiKey}}/search.php")
    suspend fun getIngredient(@Path("apiKey") apiKey: String = BuildConfig.cocktailDbApiKey, @Query("i") ingredientName: String): Response<IngredientsResponse>

}