package com.akiniyalocts.tail.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.House
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.House
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.akiniyalocts.tail.ui.cocktailDetail.CocktailDetailScreen
import com.akiniyalocts.tail.ui.favorites.FavoritesListScreen
import com.akiniyalocts.tail.ui.home.HomeScreen
import com.akiniyalocts.tail.ui.ingredients.IngredientsListScreen
import com.akiniyalocts.tail.ui.theme.TailTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.FlowPreview

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TailTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    TailBottomNavigation()
                }
            }
        }
    }
}

sealed class TopLevelScreen(
    val route: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
) {
    object HomeScreen : TopLevelScreen("Home", Icons.Filled.House, Icons.Outlined.House)
    object FavoritesScreen :
        TopLevelScreen("Favorites", Icons.Filled.Favorite, Icons.Outlined.FavoriteBorder)

    object Ingredients :
        TopLevelScreen("Ingredients", Icons.Filled.ShoppingCart, Icons.Outlined.ShoppingCart)
}

object DeepLinkScreen {
    const val drinkIdArg: String = "drinkId"
}

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterialApi::class)
@FlowPreview
@Composable
fun TailBottomNavigation() {
    val navController = rememberNavController()
    val bottomNavItems = listOf(
        TopLevelScreen.HomeScreen,
        TopLevelScreen.FavoritesScreen,
        TopLevelScreen.Ingredients
    )
    Scaffold(
        bottomBar = {
            BottomNavigation {
                val navBackStackEntry = navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry.value?.destination?.route

                bottomNavItems.forEach { screen ->
                    BottomNavigationItem(
                        selected = currentRoute == screen.route,
                        label = {
                            Text(screen.route)
                        },
                        icon = {
                            Icon(
                                imageVector = if (currentRoute == screen.route) screen.selectedIcon else screen.unselectedIcon,
                                contentDescription = screen.route
                            )
                        },
                        onClick = {
                            navController.navigate(screen.route) {

                                navController.graph.startDestinationRoute?.let { route ->
                                    popUpTo(route) {
                                        saveState = true
                                    }
                                }
                                // Avoid multiple copies of the same destination when
                                // reselecting the same item
                                launchSingleTop = true
                                // Restore state when reselecting a previously selected item
                                restoreState = true
                            }
                        },
                        alwaysShowLabel = false
                    )

                }
            }
        }
    ) {
        TailNavHost(navController = navController, it)
    }
}

@ExperimentalAnimationApi
@FlowPreview
@ExperimentalMaterialApi
@Composable
fun TailNavHost(navController: NavHostController, paddingValues: PaddingValues) {
    NavHost(
        navController = navController,
        startDestination = TopLevelScreen.HomeScreen.route,
        modifier = Modifier.padding(paddingValues)
    ) {
        composable(TopLevelScreen.HomeScreen.route) {
            HomeScreen(
                onDrinkClicked = {
                    navController.navigate("drink/$it")
                },
                onFavoritesClicked = {
                    navController.navigate(TopLevelScreen.FavoritesScreen.route)
                }
            )
        }

        composable("drink/{drinkId}") {
            CocktailDetailScreen(
                onBackPressed = {
                    navController.popBackStack()
                },
                drinkId = it.arguments?.getString(DeepLinkScreen.drinkIdArg)
            )
        }

        composable(TopLevelScreen.FavoritesScreen.route) {
            FavoritesListScreen(
                onDrinkClicked = {
                    navController.navigate("drink/${it}")
                }
            )
        }

        composable(TopLevelScreen.Ingredients.route) {
            IngredientsListScreen(
                onDrinkClicked = {
                    navController.navigate("drink/${it}")
                }
            )
        }

    }
}
