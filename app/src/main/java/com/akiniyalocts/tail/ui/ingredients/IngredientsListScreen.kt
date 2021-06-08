package com.akiniyalocts.tail.ui.ingredients

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.akiniyalocts.tail.R
import com.akiniyalocts.tail.ui.addIngredient.AddIngredientScreen
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.launch

@FlowPreview
@ExperimentalMaterialApi
@Composable
fun IngredientsListScreen(navController: NavController, viewModel: IngredientsViewModel = hiltViewModel()){
    val scaffoldState = rememberBottomSheetScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetContent = {
            AddIngredientScreen(
                scaffoldState = scaffoldState
            )
        },
        sheetShape = RoundedCornerShape(12.dp)
    ){
        UserIngredientsListScreen(viewModel){
            coroutineScope.launch {
                if(scaffoldState.bottomSheetState.isCollapsed){
                    scaffoldState.bottomSheetState.expand()
                }
            }
        }
    }
}

@Composable
fun UserIngredientsListScreen(viewModel: IngredientsViewModel, onAddNewIngredient: () -> Unit) {
    val userIngredients = viewModel.userIngredients.collectAsState()

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
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = stringResource(id = R.string.add_ingredient)
            )
        }
    )
}