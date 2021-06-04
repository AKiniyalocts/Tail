package com.akiniyalocts.tail.ui

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.outlined.LocalDrink
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.transform.RoundedCornersTransformation
import com.akiniyalocts.tail.R
import com.akiniyalocts.tail.database.FavoriteDrink
import com.akiniyalocts.tail.ui.favorites.FavoritesViewModel
import com.google.accompanist.coil.rememberCoilPainter

@Composable
fun FavoritesListScreen(navController: NavController, viewModel: FavoritesViewModel = hiltViewModel()){
    val favorites = viewModel.favoriteDrinks.collectAsState(initial = emptyList())

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.title_favorite_drinks),
                        fontWeight = FontWeight.Black,
                        fontSize = 24.sp
                    )
                },
                backgroundColor = MaterialTheme.colors.primary
            )
        }
    ) {
        if(favorites.value.isNotEmpty()) {
            LazyColumn {
                items(favorites.value) {
                    FavoriteDrinkListItem(drink = it)
                }
            }
        }else{
            FavoritesEmptyState(modifier = Modifier.fillMaxSize())
        }
    }
}


@Composable
fun FavoriteDrinkListItem(drink: FavoriteDrink){
    val expanded = remember {
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
                MaterialTheme.colors.onSecondary.copy(alpha = ButtonDefaults.OutlinedBorderOpacity),
                RoundedCornerShape(8.dp)
            )
            .clickable {

            }
    ) {
        Column(
            modifier = Modifier.animateContentSize()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
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

@Composable
fun FavoritesEmptyState(modifier: Modifier){

    Box(
        modifier.padding(top = 12.dp, bottom = 12.dp), contentAlignment = Alignment.Center
    ) {
        Row {
            Icon(imageVector = Icons.Outlined.LocalDrink, contentDescription = null, modifier = Modifier.padding(end = 8.dp), tint = MaterialTheme.colors.onSecondary)
            Text(text = "Your favorite drinks show here", color = MaterialTheme.colors.onSecondary)
        }
    }
}