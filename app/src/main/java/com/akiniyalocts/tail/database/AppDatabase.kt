package com.akiniyalocts.tail.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.akiniyalocts.tail.database.favorite.FavoriteDrink
import com.akiniyalocts.tail.database.favorite.FavoriteDrinkDao

@Database(
    entities = [FavoriteDrink::class],
    version = 1
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun getFavoriteDrinksDao(): FavoriteDrinkDao
}