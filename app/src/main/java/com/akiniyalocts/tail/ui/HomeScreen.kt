package com.akiniyalocts.tail.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.akiniyalocts.tail.R
import com.akiniyalocts.tail.api.model.Drink
import com.google.accompanist.coil.rememberCoilPainter
import com.google.accompanist.imageloading.ImageLoadState


@Composable
fun HomeScreen(navController: NavController){
    Scaffold(
        topBar = { TopAppBar(title = { Text(text = "Tail")}) }
    ) {
        HomeList(navController)
    }
}

@Composable
fun HomeList(navController: NavController, viewModel: HomeViewModel = hiltViewModel()){
    val popularItems = viewModel.popularItems
    val categories = viewModel.drinkCategories

    LazyColumn {

        item {
            Column(Modifier.fillMaxWidth()) {
                Text(stringResource(id = R.string.title_popular_drinks))

                LazyRow {
                    items(popularItems.value){ item ->
                        SmallDrinkCard(drink = item, onClick = { drink ->
                            navController.navigate("drink/${drink.idDrink}")
                        })
                    }
                }
            }
        }

        items(categories.value){
            Text(it.name, modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp))
        }
    }
}

@Composable
fun SmallDrinkCard(drink: Drink, onClick: ((Drink) -> (Unit))){

    val painter =  rememberCoilPainter(
        request = drink.drinkThumb ?: R.drawable.ic_baseline_local_drink_24
    )

    Card(
        modifier = Modifier
            .padding(4.dp)
            .height(200.dp)
            .width(180.dp)
            .clickable {
                onClick(drink)
            },
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(Modifier.fillMaxSize()) {
            Image(
                painter = painter,
                contentDescription = "${drink.drink} image",
                modifier = Modifier.weight(0.8f)
            )
            when(painter.loadState){
                ImageLoadState.Empty -> { }
                is ImageLoadState.Loading -> {
                    Box(Modifier.fillMaxSize()) {
                        CircularProgressIndicator(Modifier.align(Alignment.Center))
                    }
                }
                is ImageLoadState.Error, is ImageLoadState.Success -> { }
            }
            Text(drink.drink, modifier = Modifier
                .padding(8.dp)
                .weight(0.2f))
        }

    }
}