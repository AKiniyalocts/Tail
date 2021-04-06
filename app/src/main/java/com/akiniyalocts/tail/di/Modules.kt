package com.akiniyalocts.tail.di

import com.akiniyalocts.tail.api.CocktailApi
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

val dataModule = module {
    single {
        Retrofit.Builder()
            .baseUrl("https://www.thecocktaildb.com/")
            .addConverterFactory(MoshiConverterFactory.create())
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
    }

    single {
        get<Retrofit>().create(CocktailApi::class.java)
    }
}