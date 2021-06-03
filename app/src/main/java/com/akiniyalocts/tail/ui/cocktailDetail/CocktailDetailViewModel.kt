package com.akiniyalocts.tail.ui.cocktailDetail

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akiniyalocts.tail.api.model.Drink
import com.akiniyalocts.tail.database.FavoriteDrink
import com.akiniyalocts.tail.database.FavoriteDrinkDao
import com.akiniyalocts.tail.repo.SearchRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CocktailDetailViewModel @Inject constructor(private val searchRepo: SearchRepo, private val favoriteDrinkDao: FavoriteDrinkDao): ViewModel() {
    val drink = mutableStateOf<Drink?>(null)
    val isFavorite = mutableStateOf(false)

    var drinkId: String? = null

    set(value) {
        if(value != null){
            getDrink(value)
            checkFavorite(value)
            field = value
        }
    }

    private fun checkFavorite(value: String) = viewModelScope.launch{
        favoriteDrinkDao.isFavorite(value).distinctUntilChanged().collect {
            isFavorite.value = it
        }
    }

    fun getDrink(id: String){
        viewModelScope.launch {
            searchRepo.getDrink(id).onSuccess {
                drink.value = it
            }
        }
    }

    fun toggleFavorite(checked: Boolean) = viewModelScope.launch {
        val favoriteDrink = drink.value ?: return@launch

        if(!checked){
            favoriteDrinkDao.deleteFavorite(FavoriteDrink.fromNetwork(favoriteDrink))
        }else{
            favoriteDrinkDao.insertFavorite(FavoriteDrink.fromNetwork(favoriteDrink))
        }
    }

}