package com.akiniyalocts.tail.ui.ingredients

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akiniyalocts.tail.database.userIngredient.UserIngredient
import com.akiniyalocts.tail.repo.IngredientsRepo
import com.akiniyalocts.tail.repo.SearchRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IngredientsViewModel @Inject constructor(private val ingredientsRepo: IngredientsRepo, private val searchRepo: SearchRepo): ViewModel() {

    val screenState = mutableStateOf<IngredientsListScreenState>(IngredientsListScreenState.Loading)
    val selectedMixers = mutableStateOf( mutableSetOf<String>())

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

    fun addMixer(ingredient: UserIngredient) {
        val updated = selectedMixers.value.toMutableSet().apply {
            add(ingredient.name)
        }
        selectedMixers.value = updated
    }

    fun removeMixer(mixer: String) {
        val updated = selectedMixers.value.toMutableSet().apply {
            remove(mixer)
        }
        selectedMixers.value = updated
    }

    fun getDrinksForMixers() = viewModelScope.launch{
        searchRepo.getDrinksForMixers(selectedMixers.value).fold(
            {

            },
            {
                it.printStackTrace()
            }
        )
    }
}