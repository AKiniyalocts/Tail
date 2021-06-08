package com.akiniyalocts.tail.database.userIngredient

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface UserIngredientDao {
    @Query("SELECT * FROM USERINGREDIENT")
    fun getUserIngredients(): Flow<List<UserIngredient>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUserIngredient(userIngredient: UserIngredient)

    @Query("DELETE FROM USERINGREDIENT WHERE name= :name")
    suspend fun removeUserIngredientByName(name: String)
}