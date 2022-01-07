package com.akiniyalocts.tail.ui.search

import androidx.lifecycle.ViewModel
import com.akiniyalocts.tail.repo.SearchRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val searchRepo: SearchRepo) : ViewModel() {


}
