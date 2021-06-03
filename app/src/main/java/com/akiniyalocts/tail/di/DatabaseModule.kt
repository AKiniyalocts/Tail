package com.akiniyalocts.tail.di

import android.content.Context
import androidx.room.Room
import com.akiniyalocts.tail.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext app: Context) =
        Room.databaseBuilder(
            app,
            AppDatabase::class.java,
            "tail_db"
        ).build()


    @Singleton
    @Provides
    fun provideFavoriteDrinkDao(database: AppDatabase) = database.getFavoriteDrinksDao()
}