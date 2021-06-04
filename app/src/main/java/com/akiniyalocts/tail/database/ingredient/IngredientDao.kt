package com.akiniyalocts.tail.database.ingredient

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface IngredientDao {
    @Query("SELECT * FROM LOCALINGREDIENT")
    fun getIngredients(): Flow<List<LocalIngredient>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllIngredients( localIngredients: List<LocalIngredient>)

    @Query("DELETE FROM LOCALINGREDIENT")
    suspend fun deleteAllIngredients()

}