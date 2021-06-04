package com.akiniyalocts.tail.ui.addIngredient

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.SentimentDissatisfied
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.akiniyalocts.tail.R
import com.akiniyalocts.tail.api.model.Ingredient
import com.akiniyalocts.tail.database.ingredient.LocalIngredient

@Composable
fun AddIngredientScreen(navController: NavController, viewModel: AddIngredientViewModel = hiltViewModel()){

    val focusManager = LocalFocusManager.current
    val state = viewModel.screenState.value
    val ingredients = viewModel.allIngredients.collectAsState(initial = emptyList())

    Scaffold(
        topBar = {
            Row(Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = viewModel.query.value,
                    onValueChange = {
                        viewModel.query.value = it
                    },
                    leadingIcon = {
                        IconButton(onClick = {
                            navController.popBackStack()
                        }) {
                            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = stringResource(
                                id = R.string.navigate_back
                            ))
                        }
                    },
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    keyboardActions = KeyboardActions(
                        onSearch = {
                            //viewModel.search()
                            focusManager.clearFocus()
                        }
                    ),
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search)
                )
            }
        }
    ) {
        when(state){
            AddIngredientSearchState.Initial -> { }
            AddIngredientSearchState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                    CircularProgressIndicator()
                }
            }
            AddIngredientSearchState.Success -> {
                IngredientsList(ingredients.value)
            }
            AddIngredientSearchState.Fail -> {
                //TODO: show failure, retry button?
            }
            AddIngredientSearchState.Empty -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                    Row {
                        Icon(imageVector = Icons.Default.SentimentDissatisfied, contentDescription = null)
                        Text(stringResource(id = R.string.no_results_found))
                    }
                }
            }
        }
    }
}

@Composable
fun IngredientsList(ingredients: List<LocalIngredient>) {
    LazyColumn {
        items(ingredients){
            Text(it.name)
        }
    }
}
