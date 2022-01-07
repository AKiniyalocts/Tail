package com.akiniyalocts.tail.ui.ingredients

import com.akiniyalocts.tail.database.userIngredient.UserIngredient

sealed class IngredientsListScreenState {
    object Loading : IngredientsListScreenState()
    data class Success(val items: List<UserIngredient>) : IngredientsListScreenState()
    object Empty : IngredientsListScreenState()
}
