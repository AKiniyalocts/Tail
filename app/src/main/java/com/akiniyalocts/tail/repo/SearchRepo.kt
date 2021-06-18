package com.akiniyalocts.tail.repo

import com.akiniyalocts.tail.api.Category
import com.akiniyalocts.tail.api.CocktailApi
import com.akiniyalocts.tail.api.model.Drink
import com.akiniyalocts.tail.api.model.Ingredient
import com.akiniyalocts.tail.api.model.MixerDrink
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

interface SearchRepo {
    suspend fun searchCocktails(query: String): Result<List<Drink>>
    suspend fun getPopularCocktails(): Result<List<Drink>>
    suspend fun getCategories(): Result<List<Category>>
    suspend fun getDrink(id: String): Result<Drink>
    suspend fun getIngredients(name: String): Result<List<Ingredient>>
    suspend fun getDrinksForMixers(mixers: Set<String>): Result<List<MixerDrink>>
}
class SearchRepoImp(private val api: CocktailApi, private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO): SearchRepo{
    override suspend fun searchCocktails(query: String): Result<List<Drink>> {
        return try {
            api.getSearchResults(query = query).mapToResult{ it.drinks }
        }catch (e: Exception){
            Result.failure(e)
        }
    }

    override suspend fun getPopularCocktails(): Result<List<Drink>> {
        return try {
            api.getPopularCocktails().mapToResult{ it.drinks }
        }catch (e: Exception){
            Result.failure(e)
        }
    }

    override suspend fun getCategories(): Result<List<Category>> {
        return try{
            api.getCategories().mapToResult{ it.categories}
        }catch (e: Exception){
            Result.failure(e)
        }
    }

    override suspend fun getDrink(id: String): Result<Drink> {
        return try{
            api.getCocktail(id = id).mapToResult {
                it.drinks.first()
            }
        }catch (e: Exception){
            Result.failure(e)
        }
    }

    override suspend fun getIngredients(name: String): Result<List<Ingredient>> {
        return try {
           Result.success(emptyList())
        }catch (e: Exception){
            Result.failure(e)
        }
    }

    override suspend fun getDrinksForMixers(mixers: Set<String>): Result<List<MixerDrink>> {
        return try{
            if(mixers.isEmpty()){
                Result.failure(IllegalArgumentException("No mixers provided"))
            }else{
                val split = mixers.joinToString(",")
                val sanitized = split.replace(" ", "_")
                api.getDrinksWithMixers(mixers = sanitized).mapToResult {
                    it.drinks
                }
            }
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}