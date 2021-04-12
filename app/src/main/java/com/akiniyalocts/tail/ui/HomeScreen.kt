package com.akiniyalocts.tail.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.akiniyalocts.tail.R
import com.akiniyalocts.tail.api.model.Drink
import dev.chrisbanes.accompanist.coil.CoilImage
import org.koin.androidx.compose.getViewModel


@Composable
fun HomeScreen(){
    Scaffold(
        topBar = { TopAppBar(title = { Text(text = "Tail")}) }
    ) {
        HomeList()
    }
}

@Composable
fun HomeList(){
    val viewModel = getViewModel<HomeViewModel>()
    val popularItems = viewModel.popularItems
    val categories = viewModel.drinkCategories

    LazyColumn {

        item {
            Column(Modifier.fillMaxWidth()) {
                Text(stringResource(id = R.string.title_popular_drinks))

                LazyRow {
                    items(popularItems.value){ item ->
                        SmallDrinkCard(drink = item)
                    }
                }
            }
        }

        items(categories.value){
            Text(it.name, modifier = Modifier.fillMaxWidth().padding(8.dp))
        }
    }
}

@Composable
fun SmallDrinkCard(drink: Drink){
    Card(
        Modifier
            .padding(4.dp)
            .height(200.dp)
            .width(180.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(Modifier.fillMaxSize()) {
            CoilImage(
                data = drink.drinkThumb ?: R.drawable.ic_baseline_local_drink_24,
                contentScale = ContentScale.Crop,
                contentDescription = "${drink.drink} image",
                loading = {
                    Box(Modifier.matchParentSize()) {
                        CircularProgressIndicator(Modifier.align(Alignment.Center))
                    }
                },
                error = {
                    Image(imageVector = Icons.Filled.Person, contentDescription = "Error")
                },
                modifier = Modifier.weight(0.8f)
            )
            Text(drink.drink, modifier = Modifier
                .padding(8.dp)
                .weight(0.2f))
        }

    }
}