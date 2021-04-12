package com.akiniyalocts.tail.di

import com.akiniyalocts.tail.api.CocktailApi
import com.akiniyalocts.tail.repo.SearchRepo
import com.akiniyalocts.tail.repo.SearchRepoImp
import com.akiniyalocts.tail.ui.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
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

    single<SearchRepo>{ SearchRepoImp(get()) }
}

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
}

val all = listOf(dataModule, viewModelModule)