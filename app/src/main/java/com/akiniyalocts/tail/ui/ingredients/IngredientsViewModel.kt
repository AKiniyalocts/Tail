package com.akiniyalocts.tail.ui.ingredients

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.akiniyalocts.tail.database.localIngredient.LocalIngredient
import com.akiniyalocts.tail.database.userIngredient.UserIngredient
import com.akiniyalocts.tail.repo.IngredientsRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IngredientsViewModel @Inject constructor(private val ingredientsRepo: IngredientsRepo): ViewModel() {

    val screenState = mutableStateOf<IngredientsListScreenState>(IngredientsListScreenState.Loading)

    init {
        viewModelScope.launch {
            ingredientsRepo.userIngredientsFlow
                .distinctUntilChanged()
                .collect {
                    val state = if(it.isEmpty()) IngredientsListScreenState.Empty else IngredientsListScreenState.Success(it)
                    screenState.value = state
                }
        }
    }

    fun removeIngredient(removeIngredient: UserIngredient) = viewModelScope.launch {
        ingredientsRepo.deleteIngredient(removeIngredient)
    }
}