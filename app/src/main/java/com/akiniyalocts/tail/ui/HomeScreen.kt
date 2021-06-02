package com.akiniyalocts.tail.ui

import android.graphics.Outline
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.akiniyalocts.tail.R
import com.akiniyalocts.tail.api.model.Drink
import com.google.accompanist.coil.rememberCoilPainter
import com.google.accompanist.imageloading.ImageLoadState


@Composable
fun HomeScreen(navController: NavController){
    Scaffold(
        topBar = { TopAppBar(title = { Text(text = stringResource(id = R.string.app_name), fontWeight = FontWeight.Black, fontSize = 24.sp) }) }
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
                HomeHeaderText(stringResource = R.string.title_popular_drinks)
                LazyRow {
                    items(popularItems.value){ item ->
                        SmallDrinkCard(drink = item, onClick = { drink ->
                            navController.navigate("drink/${drink.idDrink}")
                        })
                    }
                }
            }
        }
        item {
            HomeHeaderText(stringResource = R.string.title_drink_categories)
        }
        items(categories.value.chunked(2)){
            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.padding(top = 4.dp, bottom = 4.dp)) {
                it.forEach {
                    OutlinedButton(
                        modifier = Modifier.padding(start = 16.dp, top = 4.dp, bottom = 4.dp),
                        onClick = {
                            //TODO: navigate to drinks for categories
                        }
                    ) {
                        Text(it.name)
                    }
                }
            }
        }
    }
}

@Composable
fun HomeHeaderText(stringResource: Int? = null, string: String? = null){
    val text = if(stringResource != null) stringResource(id = stringResource) else string.orEmpty()
    Text(text, fontWeight = FontWeight.Black, fontSize = 20.sp, modifier = Modifier.padding(start = 16.dp, top = 12.dp, bottom = 12.dp))
}

@Composable
fun SmallDrinkCard(drink: Drink, onClick: ((Drink) -> (Unit))){

    val painter =  rememberCoilPainter(
        request = drink.drinkThumb ?: R.drawable.ic_baseline_local_drink_24
    )

    Card(
        modifier = Modifier
            .padding(start = 16.dp, top = 4.dp, bottom = 4.dp, end = 16.dp)
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
                modifier = Modifier.weight(0.8f),
                contentScale = ContentScale.Crop
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