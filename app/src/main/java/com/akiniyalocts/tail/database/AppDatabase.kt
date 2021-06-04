package com.akiniyalocts.tail.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.akiniyalocts.tail.database.favorite.FavoriteDrink
import com.akiniyalocts.tail.database.favorite.FavoriteDrinkDao
import com.akiniyalocts.tail.database.ingredient.IngredientDao
import com.akiniyalocts.tail.database.ingredient.LocalIngredient

@Database(
    entities = [FavoriteDrink::class,  LocalIngredient::class],
    version = 2,
    exportSchema = true
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun getFavoriteDrinksDao(): FavoriteDrinkDao
    abstract fun getLocalIngredientsDao(): IngredientDao
}