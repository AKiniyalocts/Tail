package com.akiniyalocts.tail.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [FavoriteDrink::class],
    version = 1
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun getFavoriteDrinksDao(): FavoriteDrinkDao
}