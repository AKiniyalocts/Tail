package com.akiniyalocts.tail.ui.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akiniyalocts.tail.api.Category
import com.akiniyalocts.tail.api.model.Drink
import com.akiniyalocts.tail.database.favorite.FavoriteDrinkDao
import com.akiniyalocts.tail.repo.SearchRepo
import com.akiniyalocts.tail.ui.utils.AsyncState
import com.akiniyalocts.tail.ui.utils.Fail
import com.akiniyalocts.tail.ui.utils.Loading
import com.akiniyalocts.tail.ui.utils.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val searchRepo: SearchRepo, private val favoriteDrinkDao: FavoriteDrinkDao): ViewModel() {
    val popularItems = mutableStateOf(emptyList<Drink>())
    val popularItemsState = mutableStateOf<AsyncState<Unit>>(Loading)

    val drinkCategories = mutableStateOf(emptyList<Category>())

    val favoriteDrinks = favoriteDrinkDao.getFavorites().distinctUntilChanged()

    init {
        getPopularDrinks()
        getCategories()
    }

    fun getPopularDrinks() = viewModelScope.launch {
        searchRepo.getPopularCocktails().fold(
            {
                popularItems.value = it
                popularItemsState.value = Success(Unit)
            },
            {
                popularItemsState.value = Fail(it)
            }
        )
    }

    fun getCategories() = viewModelScope.launch {
        searchRepo.getCategories().onSuccess {
            drinkCategories.value = it
        }.onFailure {
            it.printStackTrace()
        }
    }

}