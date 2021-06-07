package com.akiniyalocts.tail.ui.ingredients

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.akiniyalocts.tail.R
import com.akiniyalocts.tail.Screen

@Composable
fun IngredientsListScreen(navController: NavController, viewModel: IngredientsViewModel = hiltViewModel()){
    Scaffold(
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {
            AddIngredientButton {
                navController.navigate(Screen.AddIngredientScreen.route)
            }
        }
    ){
        UserIngredientsListScreen(viewModel)
    }
}

@Composable
fun UserIngredientsListScreen(viewModel: IngredientsViewModel) {
    val userIngredients = viewModel.userIngredients

    LazyColumn {
        items(userIngredients.value){
            Text(it.name)
        }
    }
}

@Composable
fun AddIngredientButton(onAddNewIngredient: () -> Unit){
    ExtendedFloatingActionButton(
        text = {
            Text(text = stringResource(id = R.string.add_ingredient))
        },
        onClick = onAddNewIngredient,
        icon = {
            Icon(imageVector = Icons.Default.Add, contentDescription = stringResource(id = R.string.add_ingredient))
        }
    )
}