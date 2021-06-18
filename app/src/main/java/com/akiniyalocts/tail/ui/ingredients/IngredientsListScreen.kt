package com.akiniyalocts.tail.ui.ingredients

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.AddShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.akiniyalocts.tail.R
import com.akiniyalocts.tail.database.userIngredient.UserIngredient
import com.akiniyalocts.tail.ui.addIngredient.AddIngredientScreen
import com.akiniyalocts.tail.ui.cocktailDetail.TagChip
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.launch

@FlowPreview
@ExperimentalMaterialApi
@Composable
fun IngredientsListScreen(navController: NavController, viewModel: IngredientsViewModel = hiltViewModel()){
    val scaffoldState = rememberBottomSheetScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    BottomSheetScaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.title_your_ingredients),
                        fontWeight = FontWeight.Black,
                        fontSize = 24.sp
                    )
                },
                backgroundColor = MaterialTheme.colors.primary
            )
        },
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
                scaffoldState.bottomSheetState.expand()
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun UserIngredientsListScreen(viewModel: IngredientsViewModel, onAddNewIngredient: () -> Unit) {
    val state = viewModel.screenState
    val mixerItems = viewModel.selectedMixers

    when(val screenState = state.value){
       is IngredientsListScreenState.Success -> {

           Column(){
               Row(
                   Modifier
                       .fillMaxWidth()
                       .background(color = MaterialTheme.colors.surface)
                       .animateContentSize()
               ) {
                   LazyRow(Modifier.weight(1f).align(CenterVertically)) {
                       items(mixerItems.value.toList()) {
                           Spacer(Modifier.padding(4.dp))
                           TagChip(
                               text = it,
                               modifier = Modifier.clickable {
                                    viewModel.removeMixer(it)
                                }
                           )
                           Spacer(Modifier.padding(4.dp))
                       }
                   }
                   if(mixerItems.value.isNotEmpty()) {
                       IconButton(
                           onClick = {
                               //TODO: mixer search
                           },
                           modifier = Modifier.padding(end = 16.dp)
                       ) {
                           Icon(imageVector = Icons.Default.Search, null)
                       }
                   }
               }


               LazyColumn {
                   items(screenState.items) {
                       UserIngredientItem(
                           it,
                           onAddMixer = {
                               viewModel.addMixer(it)
                           }
                       ) {
                           viewModel.removeIngredient(it)
                       }
                   }
               }
           }

       }
        IngredientsListScreenState.Empty -> {
            UserIngredientEmptyState(onAddNewIngredient)
        }
        IngredientsListScreenState.Loading -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
    }

}

@Composable
fun UserIngredientEmptyState(onAddNewIngredient: () -> Unit) {
    Box(
        modifier = Modifier
            .padding(top = 12.dp, bottom = 12.dp)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        OutlinedButton(
            onClick = onAddNewIngredient
        ) {
            Icon(
                imageVector = Icons.Outlined.AddShoppingCart,
                contentDescription = null,
                modifier = Modifier.padding(end = 8.dp),
                tint = MaterialTheme.colors.onSecondary
            )
            Text(text = stringResource(id = R.string.add_some_ingredients), color = MaterialTheme.colors.onSecondary)
        }
    }
}

@Composable
fun UserIngredientItem(
    ingredient: UserIngredient,
    onAddMixer: (UserIngredient) -> Unit,
    onRemoveIngredient: (UserIngredient) -> Unit
) {
    val expanded = remember {
        mutableStateOf(false)
    }

    Column(Modifier.animateContentSize()) {
        Row(
            Modifier.padding(top = 12.dp, bottom = 12.dp, start = 16.dp, end = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = ingredient.name,
                fontSize = 16.sp,
                modifier = Modifier.weight(1f)
            )
            IconButton(
                onClick = {
                    onAddMixer(ingredient)
                },
                modifier = Modifier.padding(end = 12.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add to mixer"
                )
            }
        }
        if(expanded.value){
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedButton(
                    onClick = {

                    },
                    modifier = Modifier.padding(top = 12.dp, bottom = 12.dp)
                ) {
                    Icon(imageVector = Icons.Default.LocalDrink, contentDescription = null)
                    Text(
                        text = stringResource(id = R.string.mix_a_drink),
                        Modifier.padding(start = 8.dp)
                    )
                }
                Spacer(modifier = Modifier.padding(12.dp))
                IconButton(onClick = {
                    onRemoveIngredient(ingredient)
                }) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = stringResource(id = R.string.remove_ingredient))
                }
            }
        }
        Divider()
    }
}

