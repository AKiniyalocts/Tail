package com.akiniyalocts.tail.ui

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akiniyalocts.tail.api.model.Drink
import com.akiniyalocts.tail.repo.SearchRepo
import com.akiniyalocts.tail.ui.utils.AsyncState
import com.akiniyalocts.tail.ui.utils.Fail
import com.akiniyalocts.tail.ui.utils.Loading
import com.akiniyalocts.tail.ui.utils.Success
import kotlinx.coroutines.launch

class HomeViewModel(private val searchRepo: SearchRepo): ViewModel() {
    val popularItems = mutableStateOf(emptyList<Drink>())
    val popularItemsState = mutableStateOf<AsyncState<Unit>>(Loading)

    init {
        getPopularDrinks()
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

}