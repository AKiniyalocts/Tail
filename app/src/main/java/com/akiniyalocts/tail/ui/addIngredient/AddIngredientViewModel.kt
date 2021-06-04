package com.akiniyalocts.tail.ui.addIngredient

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akiniyalocts.tail.api.model.Ingredient
import com.akiniyalocts.tail.repo.IngredientsRepo
import com.akiniyalocts.tail.repo.SearchRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddIngredientViewModel @Inject constructor(private val ingredientsRepo: IngredientsRepo): ViewModel() {
    val screenState = mutableStateOf(AddIngredientSearchState.Initial)
    val query = mutableStateOf("")
    val results = mutableStateOf<List<Ingredient>>(emptyList())

    val allIngredients = ingredientsRepo.allIngredients.map {
        screenState.value = AddIngredientSearchState.Success
        it
    }

    init {
        refreshIngredients()
    }

    fun refreshIngredients() = viewModelScope.launch {
        ingredientsRepo.saveIngredients()
    }

}