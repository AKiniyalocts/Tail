package com.akiniyalocts.tail.di

import com.akiniyalocts.tail.api.CocktailApi
import com.akiniyalocts.tail.repo.SearchRepo
import com.akiniyalocts.tail.repo.SearchRepoImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Modules{

    @Singleton
    @Provides
    fun provideCocktailDbApi(): CocktailApi {
        return Retrofit.Builder()
            .baseUrl("https://www.thecocktaildb.com/")
            .addConverterFactory(MoshiConverterFactory.create())
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
            .create(CocktailApi::class.java)
    }

    @Singleton
    @Provides
    fun provideSearchRepo(api: CocktailApi): SearchRepo{
        return SearchRepoImp(api)
    }
}

