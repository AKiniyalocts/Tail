package com.akiniyalocts.tail.ui.ingredients

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
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

    val userIngredients = MutableStateFlow<List<UserIngredient>>(emptyList())

    init {
        viewModelScope.launch {
            ingredientsRepo.userIngredientsFlow
                .distinctUntilChanged()
                .collect {
                    userIngredients.value = it
                }

        }
    }

}