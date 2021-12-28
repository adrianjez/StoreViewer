package com.hqapps.storeviewer.ui.storelist

import android.location.Location
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.hqapps.storeviewer.data.StoreRepository
import com.hqapps.storeviewer.ui.storelist.model.StoreSearchEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StoreListViewModel @Inject constructor (
    private val repository: StoreRepository
) : ViewModel() {

    private val currentSearch = MutableLiveData<StoreSearchEntity>()

    val stores = currentSearch.switchMap { currentSearch ->
        repository.getSearchResults(
            currentSearch.latitude,
            currentSearch.longitude
        ).cachedIn(viewModelScope)
    }

    fun reloadStores(location: Location) {
        currentSearch.value = StoreSearchEntity(
            latitude = location.latitude.toFloat(),
            longitude = location.longitude.toFloat()
        )
    }

}