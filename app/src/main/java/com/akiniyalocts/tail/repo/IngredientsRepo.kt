package com.akiniyalocts.tail.repo

import com.akiniyalocts.tail.api.CocktailApi
import com.akiniyalocts.tail.database.ingredient.IngredientDao
import com.akiniyalocts.tail.database.ingredient.LocalIngredient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged

interface IngredientsRepo {
    suspend fun saveIngredients(): Result<Boolean>
    val allIngredients : Flow<List<LocalIngredient>>
}
class IngredientsRepoImp(private val api: CocktailApi, private val ingredientsDao: IngredientDao): IngredientsRepo{

    override val allIngredients = ingredientsDao.getIngredients().distinctUntilChanged()

    private val ingredientsFailure = Exception("Unable to get ingredients")

    override suspend fun saveIngredients(): Result<Boolean> {
        return try{
            val results = api.getAllIngredients().mapToResult()

            if(results.isSuccess){
                val ingredients = results.getOrNull()?.ingredients?.map {
                    LocalIngredient(it.name)
                } ?:  throw ingredientsFailure

                ingredientsDao.deleteAllIngredients()
                ingredientsDao.insertAllIngredients(ingredients)

                Result.success(true)
            }else{
                Result.failure(ingredientsFailure)
            }
        } catch (e: Exception){
            Result.failure(e)
        }

    }
}