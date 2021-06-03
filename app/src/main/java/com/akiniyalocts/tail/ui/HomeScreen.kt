package com.akiniyalocts.tail.ui

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Alignment.Companion.End
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.transform.RoundedCornersTransformation
import com.akiniyalocts.tail.R
import com.akiniyalocts.tail.api.model.Drink
import com.akiniyalocts.tail.database.FavoriteDrink
import com.google.accompanist.coil.rememberCoilPainter
import com.google.accompanist.imageloading.ImageLoadState
import com.google.android.material.color.MaterialColors
import kotlin.math.exp


@Composable
fun HomeScreen(navController: NavController){
    Scaffold(
        topBar = { TopAppBar(
            title = {
                Text(
                    text = stringResource(id = R.string.app_name),
                    fontWeight = FontWeight.Black,
                    fontSize = 24.sp
                )
            },
            backgroundColor = MaterialTheme.colors.primary
        )
        }
    ) {
        HomeList(navController)
    }
}

@Composable
fun HomeList(navController: NavController, viewModel: HomeViewModel = hiltViewModel()){
    val popularItems = viewModel.popularItems
    val categories = viewModel.drinkCategories
    val favorites = viewModel.favoriteDrinks.collectAsState(initial = emptyList())

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
            HomeHeaderText(stringResource = R.string.title_favorite_drinks)
        }


        if (favorites.value.isEmpty()) {
            item {
                Text(text = "No favorites, add some!")
            }
        } else {
            items(favorites.value) {
                FavoriteDrinkListItem(drink = it)
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
    Text(
        text = text,
        fontWeight = FontWeight.Black,
        fontSize = 20.sp,
        modifier = Modifier.padding(start = 16.dp, top = 12.dp, bottom = 12.dp)
    )
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

@Composable
fun FavoriteDrinkListItem(drink: FavoriteDrink){
    var expanded = remember {
        mutableStateOf(false)
    }

    val painter = rememberCoilPainter(
        request = drink.imageThumbnail,
        requestBuilder = {
            transformations(RoundedCornersTransformation(12f))
        }
    )
    Row(
        Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp)
            .border(
                1.dp,
                MaterialTheme.colors.primary.copy(alpha = ButtonDefaults.OutlinedBorderOpacity),
                RoundedCornerShape(8.dp)
            )
            .clickable {

            }
    ) {
        Column(
            modifier = Modifier.animateContentSize()
        ) {
            Row(
                verticalAlignment = CenterVertically
            ) {
                Image(
                    painter = painter,
                    contentDescription = drink.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .padding(end = 16.dp, start = 12.dp, top = 12.dp, bottom = 12.dp)
                        .size(80.dp)
                )
                Text(
                    text = drink.name,
                    modifier = Modifier.weight(1f)
                )

                IconButton(
                    onClick = {
                        expanded.value = !expanded.value
                    },
                    modifier = Modifier.padding(end = 12.dp)
                ) {
                    Icon(
                        imageVector = if (expanded.value) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                        contentDescription = "Expand"
                    )
                }
            }

            if(expanded.value){
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = stringResource(id = R.string.title_instructions),
                    modifier = Modifier.padding(start = 12.dp, end = 12.dp, bottom = 12.dp),
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colors.onPrimary
                )
                Text(
                    text = drink.instructions.orEmpty(),
                    modifier = Modifier.padding(start = 12.dp, end = 12.dp, bottom = 12.dp),
                    fontWeight = FontWeight.Light
                )
            }
        }
    }
}