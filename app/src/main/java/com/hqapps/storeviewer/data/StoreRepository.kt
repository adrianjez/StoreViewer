package com.hqapps.storeviewer.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.hqapps.storeviewer.api.StoreAPI
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StoreRepository @Inject constructor(private val storeAPI: StoreAPI) {

    fun getSearchResults(latitude: Float, longitude: Float) = Pager(
        config = PagingConfig(
            pageSize = 10,
            maxSize = 100,
            enablePlaceholders = false
        ),
        pagingSourceFactory = {
            StorePagingSource(
                storeAPI = storeAPI,
                latitude = latitude,
                longitude = longitude,
                radius = 1000
            )
        }
    ).liveData
}