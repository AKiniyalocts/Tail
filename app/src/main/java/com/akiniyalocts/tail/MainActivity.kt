package com.akiniyalocts.tail

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.akiniyalocts.tail.ui.HomeScreen
import com.akiniyalocts.tail.ui.cocktailDetail.CocktailDetailScreen
import com.akiniyalocts.tail.ui.theme.TailTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TailTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    TailNavHost()
                }
            }
        }
    }
}

sealed class Screen(val route: String){
    object HomeScreen: Screen("Home")
}

object DeepLinkScreen {
    const val drinkIdArg: String = "drinkId"
}
@Composable
fun TailNavHost(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.HomeScreen.route){
        composable(Screen.HomeScreen.route){
            HomeScreen(navController)
        }

        composable("drink/{drinkId}"){
            CocktailDetailScreen(it.arguments?.getString(DeepLinkScreen.drinkIdArg))
        }
    }
}