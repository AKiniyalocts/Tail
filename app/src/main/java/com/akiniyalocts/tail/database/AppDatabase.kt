package com.akiniyalocts.tail.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.akiniyalocts.tail.database.favorite.FavoriteDrink
import com.akiniyalocts.tail.database.favorite.FavoriteDrinkDao
import com.akiniyalocts.tail.database.localIngredient.LocalIngredient
import com.akiniyalocts.tail.database.localIngredient.LocalIngredientDao
import com.akiniyalocts.tail.database.userIngredient.UserIngredient
import com.akiniyalocts.tail.database.userIngredient.UserIngredientDao

@Database(
    entities = [FavoriteDrink::class, LocalIngredient::class, UserIngredient::class],
    version = 4,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getFavoriteDrinksDao(): FavoriteDrinkDao
    abstract fun getLocalIngredientsDao(): LocalIngredientDao
    abstract fun getUserIngredientsDao(): UserIngredientDao
}
