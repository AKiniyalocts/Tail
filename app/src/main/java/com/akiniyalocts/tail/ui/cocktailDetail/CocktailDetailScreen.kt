package com.akiniyalocts.tail.ui.cocktailDetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.akiniyalocts.tail.R
import com.akiniyalocts.tail.api.model.Drink
import com.google.accompanist.coil.rememberCoilPainter

@Composable
fun CocktailDetailScreen(drinkId: String?, viewModel: CocktailDetailViewModel = hiltViewModel()){
    val drink = viewModel.drink.value
   viewModel.drinkId = drinkId

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = drink?.drink.orEmpty())},
                navigationIcon = { IconButton(onClick = {} ){ Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back") } }
            )
        }
    ) {
        drink?.let {
            CocktailDetailContent(drink = it)
        }
    }
}

@Composable
fun CocktailDetailContent(drink: Drink){
    val painter = rememberCoilPainter(drink.drinkThumb ?: R.drawable.ic_baseline_local_drink_24)

    LazyColumn {
        item{
            Image(
                painter = painter,
                contentScale = ContentScale.Crop,
                contentDescription = "${drink.drink} image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            )
        }

        item {
            Text(text = drink.instructions)
        }
    }
}