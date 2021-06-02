package com.akiniyalocts.tail.ui.cocktailDetail

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akiniyalocts.tail.api.model.Drink
import com.akiniyalocts.tail.repo.SearchRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CocktailDetailViewModel @Inject constructor(private val searchRepo: SearchRepo): ViewModel() {
    val drink = mutableStateOf<Drink?>(null)
    var drinkId: String? = null

    set(value) {
        if(value != null){
            getDrink(value)
            field = value
        }
    }

    fun getDrink(id: String){
        viewModelScope.launch {
            searchRepo.getDrink(id).onSuccess {
                drink.value = it
            }
        }
    }

}